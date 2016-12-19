package com.cn.rtmp;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Map;

import com.smaxe.io.ByteArray;
import com.smaxe.uv.client.INetStream;
import com.smaxe.uv.client.NetStream;
import com.smaxe.uv.client.camera.AbstractCamera;
import com.smaxe.uv.stream.support.MediaDataByteArray;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class CameraVideoActivity extends Activity {
	private TextView hour; // Сʱ
	private TextView minute; // ����
	private TextView second; // ��
	private Button mStart; // ��ʼ��ť
	private Button mStop; // ������ť
	private Button mReturn; // ���ذ�ť
	private static AndroidCamera aCamera;
	private boolean streaming;
	private boolean isTiming = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.video);

		init();

	}

	private void init() {
		aCamera = new AndroidCamera(CameraVideoActivity.this);
		hour = (TextView) findViewById(R.id.mediarecorder_TextView01);
		minute = (TextView) findViewById(R.id.mediarecorder_TextView03);
		second = (TextView) findViewById(R.id.mediarecorder_TextView05);
		mStart = (Button) findViewById(R.id.mediarecorder_VideoStartBtn);
		mStop = (Button) findViewById(R.id.mediarecorder_VideoStopBtn);
		mReturn = (Button) findViewById(R.id.mediarecorder_VideoReturnBtn);
		// ��ʼ¼��
		mStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (streaming == false) {
					aCamera.start();
				}
				aCamera.startVideo();
				isTiming = true;
				handler.postDelayed(task, 1000);// ���ð�ť״̬
				mStart.setEnabled(false);
				mReturn.setEnabled(false);
				mStop.setEnabled(true);
			}
		});
		mReturn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (RTMPConnectionUtil.netStream != null) {
					RTMPConnectionUtil.netStream.close();
					RTMPConnectionUtil.netStream = null;
				}
				if (aCamera != null) {
					aCamera = null;

				}
				finish();
			}
		});
		mStop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				aCamera.stop();
				// ���ð�ť״̬
				mStart.setEnabled(true);
				mReturn.setEnabled(true);
				mStop.setEnabled(false);
				isTiming = false;
			}
		});
	}

	public class AndroidCamera extends AbstractCamera implements SurfaceHolder.Callback, Camera.PreviewCallback {

		private SurfaceView surfaceView;
		private SurfaceHolder surfaceHolder;
		private Camera camera;

		private int width;
		private int height;

		private boolean init;

		int blockWidth;
		int blockHeight;
		int timeBetweenFrames; // 1000 / frameRate
		int frameCounter;
		byte[] previous;

		public AndroidCamera(Context context) {

			surfaceView = (SurfaceView) findViewById(R.id.mediarecorder_Surfaceview);
			surfaceView.setVisibility(View.VISIBLE);
			surfaceHolder = surfaceView.getHolder();
			surfaceHolder.addCallback(AndroidCamera.this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

			width = 352 / 2;
			height = 288 / 2;

			init = false;
			Log.d("DEBUG", "AndroidCamera()");
		}

		private void startVideo() {
			Log.d("DEBUG", "startVideo()");

			RTMPConnectionUtil.ConnectRed5(CameraVideoActivity.this);
			RTMPConnectionUtil.netStream = new UltraNetStream(RTMPConnectionUtil.connection);
			RTMPConnectionUtil.netStream.addEventListener(new NetStream.ListenerAdapter() {

				@Override
				public void onNetStatus(final INetStream source, final Map<String, Object> info) {
					Log.d("DEBUG", "Publisher#NetStream#onNetStatus: " + info);

					final Object code = info.get("code");

					if (NetStream.PUBLISH_START.equals(code)) {
						if (CameraVideoActivity.aCamera != null) {
							RTMPConnectionUtil.netStream.attachCamera(aCamera, -1 /* snapshotMilliseconds */);
							Log.d("DEBUG", "aCamera.start()");
							aCamera.start();
						} else {
							Log.d("DEBUG", "camera == null");
						}
					}
				}
			});

			RTMPConnectionUtil.netStream.publish("bbb", NetStream.LIVE);
		}

		public void start() {
			camera.startPreview();
			streaming = true;
		}

		public void stop() {
			camera.stopPreview();
			streaming = false;
		}

		public void printHexString(byte[] b) {
			for (int i = 0; i < b.length; i++) {
				String hex = Integer.toHexString(b[i] & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}

			}
		}

		@Override
		public void onPreviewFrame(byte[] arg0, Camera arg1) {
			// TODO Auto-generated method stub
			// if (!active) return;
			if (!init) {
				blockWidth = 32;
				blockHeight = 32;
				timeBetweenFrames = 100; // 1000 / frameRate
				frameCounter = 0;
				previous = null;
				init = true;
			}
			final long ctime = System.currentTimeMillis();

			/** ���ɼ���YUV420SP����ת��ΪRGB��ʽ */
			byte[] current = RemoteUtil.decodeYUV420SP2RGB(arg0, width, height);
			try {
				//

				final byte[] packet = RemoteUtil.encode(current, previous, blockWidth, blockHeight, width, height);
				fireOnVideoData(new MediaDataByteArray(timeBetweenFrames, new ByteArray(packet)));
				previous = current;
				if (++frameCounter % 10 == 0)
					previous = null;

			} catch (Exception e) {
				e.printStackTrace();
			}
			final int spent = (int) (System.currentTimeMillis() - ctime);
			try {

				Thread.sleep(Math.max(0, timeBetweenFrames - spent));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			camera = Camera.open();
			try {
				camera.setPreviewDisplay(surfaceHolder);
				camera.setPreviewCallback(this);
				Camera.Parameters params = camera.getParameters();
				params.setPreviewSize(width, height);
				camera.setParameters(params);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				camera.release();
				camera = null;
			}

			Log.d("DEBUG", "surfaceCreated()");
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			if (camera != null) {
				camera.stopPreview();
				camera.release();
				camera = null;
			}

		}

	}

	private Handler handler = new Handler();
	int s, h, m, s1, m1, h1;
	private Runnable task = new Runnable() {
		public void run() {
			if (isTiming) {
				handler.postDelayed(this, 1000);
				s++;
				if (s < 60) {
					second.setText(format(s));
				} else if (s < 3600) {
					m = s / 60;
					s1 = s % 60;
					minute.setText(format(m));
					second.setText(format(s1));
				} else {
					h = s / 3600;
					m1 = (s % 3600) / 60;
					s1 = (s % 3600) % 60;
					hour.setText(format(h));
					minute.setText(format(m1));
					second.setText(format(s1));
				}
			}
		}
	};

	public String format(int i) {
		String s = i + "";
		if (s.length() == 1) {
			s = "0" + s;
		}
		return s;
	}
}
