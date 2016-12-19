package com.example.ffmpeg1;

import android.os.Bundle;
import android.os.Environment;

import com.example.android_my_ffmpeg_demo1.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MyDecode extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.decode);
        
		Button startButton = (Button) this.findViewById(R.id.button_start);
		final EditText urlEdittext_input= (EditText) this.findViewById(R.id.input_url);
		final EditText urlEdittext_output= (EditText) this.findViewById(R.id.output_url);
		
		startButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0){			
				String folderurl=Environment.getExternalStorageDirectory().getPath();
				
				String urltext_input=urlEdittext_input.getText().toString();
		        String inputurl=folderurl+"/"+urltext_input;
		        
		        String urltext_output=urlEdittext_output.getText().toString();
		        String outputurl=folderurl+"/"+urltext_output;
		        
		        Log.i("inputurl",inputurl);
		        Log.i("outputurl",outputurl);
		    
		        
		        
		        Thread a=new thread_decode(inputurl,outputurl);
		        a.start();
		        
		        
		        Intent intent=new Intent();
		        intent.setClass(MyDecode.this, MainActivity.class);
		        startActivity(intent);
//		        decode(inputurl,outputurl);
		        
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
   
    
    
    public class thread_decode extends Thread{
    	String inputurl,outputurl;
    	public thread_decode(String inputurl,String outputurl) {
			// TODO Auto-generated constructor stub
    		this.inputurl=inputurl;
    		this.outputurl=outputurl;
		}
    	public void run(){
    		decode(inputurl, outputurl);
    	}
    }
    
    
    
    
    //JNI
    public native int decode(String inputurl, String outputurl);
    
    static{
//    	System.loadLibrary("avutil-54");
//    	System.loadLibrary("swresample-1");
//    	System.loadLibrary("avcodec-56");
//    	System.loadLibrary("avformat-56");
//    	System.loadLibrary("swscale-3");
//    	System.loadLibrary("postproc-53");
//    	System.loadLibrary("avfilter-5");
//    	System.loadLibrary("avdevice-56");
    	System.loadLibrary("sffdecoder");
    }
}


