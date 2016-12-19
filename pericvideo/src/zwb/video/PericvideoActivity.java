package zwb.video;

import java.io.IOException;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;

import android.os.Bundle;
import android.util.Log;
import android.view.Surface;

import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class PericvideoActivity extends Activity implements Callback, PreviewCallback {
	public static final String TAG = "CameraActivity";
	Camera mCamera;
	SurfaceView mRemoteView;
	Surface mLocalSurface;
	SurfaceView mLocalView;
	SurfaceHolder mLocalSurfaceHolder;
	SurfaceHolder mRemoteSurfaceHolder;
	ImageView imageView;

	boolean remoteVideoVisible = true;
	int previewWidth = 240, previewHeight = 240;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Window win = getWindow();

		win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置标志
		requestWindowFeature(Window.FEATURE_NO_TITLE);//请求窗口特征
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置请求定位
		setContentView(R.layout.main);

		mRemoteView = (SurfaceView) findViewById(R.id.remoteView);
		mLocalView = (SurfaceView) findViewById(R.id.localView);

		mLocalSurfaceHolder = mLocalView.getHolder();
		mLocalSurfaceHolder.addCallback(this);
		mLocalSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mLocalSurface = mLocalSurfaceHolder.getSurface();

		mRemoteSurfaceHolder = mRemoteView.getHolder();
		mRemoteSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_GPU);
		// imageView= (ImageView) findViewById(R.id.iv);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// Log.i(TAG, "surfaceCreated");
		mCamera = Camera.open();
		mCamera.setPreviewCallback(this);
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (IOException exception) {
			mCamera.release();
			mCamera = null;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// Log.i(TAG, "surfaceChanged");
		Camera.Parameters parameters = mCamera.getParameters();
		// parameters.setPreviewSize(previewWidth, previewHeight);
		parameters.setPreviewFormat(PixelFormat.YCbCr_420_SP);
		parameters.setPreviewFrameRate(10);
		mCamera.setParameters(parameters);
		mCamera.startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
		// Log.i(TAG, "surfaceDestroyed");
		if (mCamera != null) {
			mCamera.setPreviewCallback(null);
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	}

	@Override
	//摄像头预览框
	public void onPreviewFrame(byte[] data, Camera camera) {
		if (remoteVideoVisible) {
			int w = camera.getParameters().getPreviewSize().width;
			int h = camera.getParameters().getPreviewSize().height;
			drawRemoteVideo(data, w, h);
		}
	}

	//画布框
	private void drawRemoteVideo(final byte[] imageData, int width, int height) {
		int[] rgb = decodeYUV420SP(imageData, width, height);
		Bitmap bmp = Bitmap.createBitmap(rgb, width, height, Bitmap.Config.ARGB_8888);
		bmp = Bitmap.createScaledBitmap(bmp, mRemoteView.getWidth(), mRemoteView.getHeight(), true);
		Log.e("tag",width+"--"+height);
		Canvas canvas = mRemoteSurfaceHolder.lockCanvas();
		canvas.drawBitmap(bmp, 0, 0, null);
		mRemoteSurfaceHolder.unlockCanvasAndPost(canvas);
	}

	public int[] decodeYUV420SP(byte[] yuv420sp, int width, int height) {
		final int frameSize = width * height;
		int rgb[] = new int[width * height];
		for (int j = 0, yp = 0; j < height; j++) {
			int uvp = frameSize + (j >> 1) * width, u = 0, v = 0;
			for (int i = 0; i < width; i++, yp++) {
				int y = (0xff & ((int) yuv420sp[yp])) - 16;
				if (y < 0)
					y = 0;
				if ((i & 1) == 0) {
					v = (0xff & yuv420sp[uvp++]) - 128;
					u = (0xff & yuv420sp[uvp++]) - 128;
				}

				int y1192 = 1192 * y;
				int r = (y1192 + 1634 * v);
				int g = (y1192 - 833 * v - 400 * u);
				int b = (y1192 + 2066 * u);

				if (r < 0)
					r = 0;
				else if (r > 262143)
					r = 262143;
				if (g < 0)
					g = 0;
				else if (g > 262143)
					g = 262143;
				if (b < 0)
					b = 0;
				else if (b > 262143)
					b = 262143;

				rgb[yp] = 0xff000000 | ((r << 6) & 0xff0000) | ((g >> 2) & 0xff00) | ((b >> 10) & 0xff);

			}
		}
		return rgb;
	}



}