package com.example.videoplay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
	SocketClient socketClient; 
	private ListView listView; 
	String[] s;
	String sname;
	
	protected void onCreate(Bundle savedInstanceState) {  
		super.onCreate(savedInstanceState);  	
		setContentView(R.layout.activity_main);	
		findv();
		socketClient=new SocketClient();
		new Thread(new myRunnable()).start();	
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				String myputname=s[arg2];
				Toast.makeText(getApplicationContext(), "点击了"+myputname,
					     Toast.LENGTH_SHORT).show();
				
				Intent intent=new Intent();
				intent.setClass(MainActivity.this,PlayV.class);
				
				Bundle bundle=new Bundle();
				bundle.putString("name", myputname);
			    intent.putExtras(bundle);
			    
			    startActivity(intent);
			}			
		});
	} 
	
	protected  void findv(){
		listView=(ListView) findViewById(R.id.Lv);	
	}
	
	Handler mHandler=new Handler(){
		public void handleMessage(Message msg){
			Log.e("error", "啦啦啦啦啦");
			if(socketClient.builder!=null){
				sname=socketClient.builder.toString();
				s=sname.split(" ");
				listView.setAdapter(new ArrayAdapter<String>
				(MainActivity.this,android.R.layout.simple_expandable_list_item_1,s));
				listView.setVisibility(View.VISIBLE);
			}
			else {
				Toast.makeText(getApplicationContext(), "请检查网络",
					     Toast.LENGTH_SHORT).show();
			}			
		}
	};
	
	class myRunnable implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message message=new Message();		
			socketClient.run();
			mHandler.sendMessage(message);		
			}	
	}
}
