package com.jacky.video;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.MediaCodec.BufferInfo;
import android.os.Bundle;

import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.RelativeLayout;


import java.nio.ByteBuffer;

public class AndroidDisplay extends Activity implements SurfaceHolder.Callback{

	private DatagramSocket mDatagramSocket;
	//private DatagramPacket upack;
	RtpSocket rtp_socket = null;
	byte[] toteldata = new byte[2000];
	int totelbyteCount = 0;
	RTPHeader mRTPHeader = new RTPHeader();
	
	int sumto = 0;
	int count = 0;
	PlayerThread mPlayerThread;
	FileOutputStream outStream = null;
	//final String TESTFILE = Environment.getExternalStorageDirectory().getAbsolutePath()+"/spytest.mp4";
	Surface surface;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*屏幕常亮*/
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		/*无标题*/
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
//		File f = new File(Environment.getExternalStorageDirectory(), "video_encoded.264");
//
//	    try {
//	    	outStream = new FileOutputStream(f);
//	        Log.i("AvcEncoder", "outputStream initialized");
//	    } catch (Exception e){ 
//	        e.printStackTrace();
//	    }
	    
//		/*全屏*/
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		
		//setContentView(R.layout.activity_android_display);
		SurfaceView sv = new SurfaceView(this);
//		RelativeLayout.LayoutParams params =  new RelativeLayout.LayoutParams(300, 400); 
//		sv.setLayoutParams(params);
//		sv.getHolder().setSizeFromLayout();
		sv.getHolder().addCallback((Callback) this);
		setContentView(sv);
		//setContentView(sv, params);
	}

	protected void onDestroy() {
		//mPlayer.realse();
		super.onDestroy();
		mPlayerThread.realse();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		holder.setSizeFromLayout();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		mPlayerThread =	new PlayerThread(holder.getSurface());
		mPlayerThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}
	

	class PlayerThread extends Thread
	{
		private MediaCodec decoder;

		public static final String TAG = "DEecoseActivity";

		//private Bitmap bitmap;
		public void realse() {
			if (decoder != null) {
				isHasNext = false;
				decoder.stop();
				decoder.release();
				decoder = null;
				
			}

		}
		//h263
//		int w = 176;
//		int h = 144;
//		int fps = 7;
		
		//h264
		int w = 176;
		int h = 144;
		int fps = 10;
		int biterate = 100000;
		boolean isHasNext = true;
		//private byte[] byteBuffer=new byte[4048];
		RtpPacket rtp_packet;
		public PlayerThread(Surface surface) {
			// TODO Auto-generated constructor stub
			
			try {
				mDatagramSocket = new DatagramSocket(50000);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rtp_socket = new RtpSocket(mDatagramSocket);
			//upack =  new DatagramPacket(byteBuffer, byteBuffer.length);
			byte[] buffer = new byte[2000+12];
			rtp_packet = new RtpPacket(buffer, 0);
			MediaFormat mediaFormat = MediaFormat.createVideoFormat(
					"video/avc", w, h);
			mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, biterate);
			mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, fps);
			mediaFormat
					.setInteger(
							MediaFormat.KEY_COLOR_FORMAT,
							MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar);
			String mime = mediaFormat.getString(MediaFormat.KEY_MIME);
			mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 5);  
			decoder = MediaCodec.createDecoderByType(mime);
			if (decoder == null) {
				Log.e("DecodeActivity", "createDecoderByType fail!");
				return;
			}
			decoder.configure(mediaFormat, surface, null, 0);
			decoder.start();
		}
		@Override
		public void run() {
	
			// get in/output buffer memory area from decoder
			ByteBuffer[] inputBuffers = decoder.getInputBuffers();
			//ByteBuffer[] outputBuffers = decoder.getOutputBuffers();
			// read some H264 raw data
			byte[] datarecv = new byte[2000];
			byte[] data = new byte[2000];
			//int lengthrecv = 0;
			long timeoutUs = 0;
			long timestamp = 0l;

			while (isHasNext) {
				/** input ***/
				try {
					//mDatagramSocket.receive(upack);
					rtp_socket.receive(rtp_packet);

					//lengthrecv = upack.getLength();
//					if(lengthrecv == 6){
//						continue;
//					}
					int i = rtp_packet.getCscrCount();
					timestamp = rtp_packet.getTimestamp();
					data = rtp_packet.getPayload();
					int m = rtp_packet.getSequenceNumber();
					int n = rtp_packet.getVersion();
					boolean f = rtp_packet.hasMarker();
					if (sumto+1 != m)
					{
						Log.i("myTag", "cjh "+m);
						sumto = m;
						continue;
						//return;
					}
					sumto = m;
					
					System.arraycopy(rtp_packet.getPacket(), 0, datarecv, 0, rtp_packet.getLength());
							 
					
					//CombineDatah263(datarecv, rtp_packet.getLength());
					CombineDatah264shouji(datarecv, rtp_packet.getLength());
					//CombineDatah264(datarecv, lengthrecv);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(totelbyteCount > 0)
				{
				int inputBufferIndex = decoder
						.dequeueInputBuffer(timeoutUs);
				if (inputBufferIndex >= 0) {

					ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];  
					inputBuffer.clear();  
					inputBuffer.put(toteldata, 0, totelbyteCount);	
					
					
					
						// put raw data to inputBuffer of decoder
						//inputBuffers[inputBufferIndex].rewind();
//						inputBuffers[inputBufferIndex].put(data, 0,
//								byteCount);
						//inputBuffers[inputBufferIndex].put(toteldata, 0,
						//		totelbyteCount);
						// get a inputBuffer memory index
						//Log.i(TAG, "inputBufferIndex" + inputBufferIndex);
						// call decoder that inputBuffer has data
						decoder.queueInputBuffer(inputBufferIndex, 0,
								totelbyteCount, timestamp, 0);
						//timestamp += 40 * 1000;
					//}	
				}

				/** ouput ***/
				// get a outputBuffer memory index
				BufferInfo bufferInfo = new BufferInfo();
				// if inputBuffer is null ,this fun will be blocking
				int outIndex = decoder.dequeueOutputBuffer(bufferInfo,
						0);
				//Log.d(TAG, "dequeueOutputBuffer index " + outIndex);
				
				while (outIndex >= 0) {  
					decoder.releaseOutputBuffer(outIndex, true);  
					           outIndex = decoder.dequeueOutputBuffer(bufferInfo, 0);  
					       } 
				
//				if ((bufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
//					Log.d("DecodeActivity",
//							"OutputBuffer BUFFER_FLAG_END_OF_STREAM is over");
//					break;
//					}
				
					for(int i = 0; i < totelbyteCount; i++)
					{
						toteldata[i] = 0;			
					}
					totelbyteCount = 0;
				}
			}
				

			Log.w(TAG, "decode over");
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_android_display, menu);
		return true;
	}
	
	public void CombineDatah263(byte[]data, int len){	
		
			if (data[12] == 4&& data[13] == 0){
				
				System.arraycopy(data, 12, toteldata, 0, len - 12);
				
//				for(int j = 0; j < len - 12; j++){
//					toteldata[j] = data[j+12];
//				}
				toteldata[0] = 0;
				toteldata[1] = 0;
				totelbyteCount = len - 12;
			}
			else{
				System.arraycopy(data, 14, toteldata, 0, len - 14);
//				for(int j = 0; j < len - 14; j++){
//					toteldata[j] = data[j+14];
//				}
				totelbyteCount = len - 14;
			}
			                   
//			 try {
//				outStream.write(toteldata, 0, totelbyteCount);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

	}
	
	public void CombineDatah264(byte[]data, int len){	
		mRTPHeader.version = data[12] & 0xc0;
		
		mRTPHeader.marker = data[13] & 0x80;
		mRTPHeader.payloadtype = data[13] & 0x7f;
		//mRTPHeader.timestamp = data[4] &data[5]&data[6]&data[7];
		mRTPHeader.F = data[24] & 0x80;
		mRTPHeader.NRI = data[24] & 0x60;
		mRTPHeader.type = data[24] & 0x1f;
		
		if (mRTPHeader.type >0 && mRTPHeader.type < 24){
			for(int j = 0; j < len - 24; j++){
				toteldata[j+4] = data[j+24];
			}
			toteldata[0] = 0x00;
			toteldata[1] = 0x00;
			toteldata[2] = 0x00;
			toteldata[3] = 0x01;
			totelbyteCount = len - 24 + 4;
		}
		else if (mRTPHeader.type == 28){
			if(mRTPHeader.marker != 0){
				for(int j = 0; j < len - 26; j++){
					toteldata[j] = data[j+26];
				}
				totelbyteCount = len - 26;
			}
			else{		
				byte F;
				byte NRI;
				byte TYPE;
				byte nh;
				mRTPHeader.S = data[25] & 0x80;
				if (mRTPHeader.S >0){
					F = (byte) (data[24] & 0x80);
					NRI = (byte) (data[24] & 0x60);
					TYPE = (byte) (data[25] & 0x1f);
					nh = (byte) (F | NRI | TYPE);
					for(int j = 0; j < len - 26; j++){
						toteldata[j+5] = data[j+26];
					}
					toteldata[0] = 0x00;
					toteldata[1] = 0x00;
					toteldata[2] = 0x00;
					toteldata[3] = 0x01;
					toteldata[4] = (byte) nh;
					totelbyteCount = len - 26 + 4 + 1;
				}
				else{
					for(int j = 0; j < len - 26; j++){
						toteldata[j] = data[j+26];
					}
					totelbyteCount = len - 26;
				}
				 
			}
		}
		
	}
	
	public void CombineDatah264shouji(byte[]data, int len){	
		mRTPHeader.version = data[0] & 0xc0;
		
		mRTPHeader.marker = data[1] & 0x80;
		mRTPHeader.payloadtype = data[1] & 0x7f;
		mRTPHeader.F = data[12] & 0x80;
		mRTPHeader.NRI = data[12] & 0x60;
		mRTPHeader.type = data[12] & 0x1f;
		int m = data[2]>=0?data[2] :data[2]+256;
		int k = m<<8;
		int lenss = data[3]>0?data[3] :data[3]+256;
		int sum = k+lenss;
		if (sumto+1 != sum)
		{
			//Log.i("myTag", "cjh "+sum);
			sumto = sum;
			//return;
		}
		sumto = sum;
		
		if (mRTPHeader.type >0 && mRTPHeader.type < 24){
			for(int j = 0; j < len - 12; j++){
				toteldata[j+4] = data[j+12];
			}
			toteldata[0] = 0x00;
			toteldata[1] = 0x00;
			toteldata[2] = 0x00;
			toteldata[3] = 0x01;
			totelbyteCount = len - 12 + 4;
		}
		else if (mRTPHeader.type == 28){
			if(mRTPHeader.marker != 0){
				for(int j = 0; j < len - 14; j++){
					toteldata[j] = data[j+14];
				}
				totelbyteCount = len - 14;
			}
			else{		
				byte F;
				byte NRI;
				byte TYPE;
				byte nh;
				mRTPHeader.S = data[13] & 0x80;
				if (mRTPHeader.S >0){
					F = (byte) (data[12] & 0x80);
					NRI = (byte) (data[12] -28);
					//NRI = (byte) (data[12] & 0x60);
					TYPE = (byte) (data[13]-0x80);
					//TYPE = (byte) (data[13] & 0x1f);
					nh = (byte) (F | NRI | TYPE);
					for(int j = 0; j < len - 14; j++){
						toteldata[j+5] = data[j+14];
					}
					toteldata[0] = 0x00;
					toteldata[1] = 0x00;
					toteldata[2] = 0x00;
					toteldata[3] = 0x01;
					toteldata[4] = (byte) nh;
					totelbyteCount = len - 14 + 4 + 1;
				}
				else{
					for(int j = 0; j < len - 14; j++){
						toteldata[j] = data[j+14];
					}
					totelbyteCount = len - 14;
				}
				 
			}
		}
//		 try {
//			outStream.write(toteldata, 0, totelbyteCount);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		
	}
}
