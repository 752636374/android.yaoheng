package com.example.videoplay;

import java.io.File;
import java.io.IOException;
import com.playvideo.Play1;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

public class PlayV extends Activity {
	
	ProgressBar progressBar;
	TextView startTime, endTime;
	ImageButton play1, previous1, next1, sound1, fullscreen;
	String mygetname;
	String mypath1 = "amyvideo";
	String mypath = "aTest_Movie.mp4";
	VideoView videoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play);
		findv();
		Bundle bundle = this.getIntent().getExtras();
		mygetname = bundle.getString("name");
		
		String path ="http://192.168.0.13:5080/oflaDemo/index.html/Avengers2.mp4";
		Uri uri = Uri.parse(path);
		videoView.setMediaController(new MediaController(PlayV.this));
		videoView.setVideoURI(uri);
		videoView.requestFocus();
		videoView.start(); 
		
		
		
		
		
//		createmyfile();
//		printMsg();//打印线程名称	
//		new Thread(updateThread).start();
		
	}
	
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
                Log.e("error", "开始播放" );
                printMsg();//打印线程名称
				String path =  Environment.getExternalStorageDirectory()+ File.separator + "amyvideo" + File.separator
						+ "aTest_Movie.mp4";
				Uri uri = Uri.parse(path);
				videoView.setMediaController(new MediaController(PlayV.this));
				videoView.setVideoURI(uri);
				videoView.requestFocus();
				videoView.start();  	
		}
	};
	
	Runnable updateThread =  new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Play1 play1 = new Play1(mygetname);
			play1.getvideo();
			Message message=new Message();
			mHandler.sendMessage(message);
		}
	};

	protected void findv() {
		videoView = (VideoView) findViewById(R.id.videoView);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		startTime = (TextView) findViewById(R.id.startTime);
		endTime = (TextView) findViewById(R.id.endTime);
		play1 = (ImageButton) findViewById(R.id.play1);
		previous1 = (ImageButton) findViewById(R.id.previous1);
		next1 = (ImageButton) findViewById(R.id.next1);
		sound1 = (ImageButton) findViewById(R.id.sound1);
		fullscreen = (ImageButton) findViewById(R.id.fullscreen);
	}

	public void createmyfile() {
		
		File file1 = new File(Environment.getExternalStorageDirectory(), mypath1);
		File file2 = new File(Environment.getExternalStorageDirectory() + File.separator + "amyvideo", mypath);
		boolean myfile = false;
		boolean mydir = false;
		if (!file1.exists()) {
			mydir = file1.mkdirs();
			Log.e("error", "创建文件夹" + mydir);
		}
		if (!file2.exists()) {
			try {
				myfile = file2.createNewFile();
				Log.e("error", "创建视频文件" + myfile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			Log.e("error", "MYFILE值为" + myfile);
			file2.delete();
			Log.e("error", "删除FILE");
			try {
				myfile = file2.createNewFile();
				Log.e("error", "创建" + myfile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void printMsg() {
	      Thread t = Thread.currentThread();
	      String name = t.getName();
	      Log.e("error", "当前线程名称" +name);
	   } 
	
}