package com.example.video;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.R.anim;
import android.R.string;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity { ;
	SocketClient socketClient; 
	private ListView listview; 
	Handler mHandler=new Handler(){
		public void handleMessage(Message msg){
			Log.e("error", "啦啦啦啦啦");
			listview=(ListView) findViewById(R.id.Lv);
			listview.setAdapter(new ArrayAdapter<String>
			(MainActivity.this, android.R.layout.simple_expandable_list_item_1,socketClient.data));
			listview.setVisibility(View.VISIBLE);
		}
	};
	@Override 
	protected void onCreate(Bundle savedInstanceState) {  
		super.onCreate(savedInstanceState);  
	
		setContentView(R.layout.activity_main);	
		new Thread(new myRunnable()).start();	
	} 
	class myRunnable implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message message=new Message();		
			Log.e("error", "嘻嘻嘻");
			socketClient=new SocketClient();
				socketClient.run();
			Log.e("error", "可可可可");
			mHandler.sendMessage(message);		
			
			}
		
	}

}
