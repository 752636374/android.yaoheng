package com.example.video;
import android.location.GpsStatus.Listener;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

class SocketClient{
	StringBuilder builder;
	List<String> data;
	public SocketClient() {
		// TODO Auto-generated constructor stub
		this.data=data;
		this.builder=builder;
		
	}
	public void run(){
		try {
			data=new ArrayList<String>();
			int port=8919;
			String host="192.168.0.35";
			Log.e("error", "爱爱啊");
			Socket socket=new Socket(host, port);
			Log.e("error", "还晕乎乎");
			//Reader reader=new InputStreamReader(socket.getInputStream());
			InputStreamReader reader2=new InputStreamReader(socket.getInputStream());
			char chars[]=new char[1024];
			int len;
			builder=new StringBuilder();
			while((len=reader2.read(chars))!=-1){
				builder.append(chars,0,len);
			}					
			socket.close();
			data.add(builder.toString());
			if(data!=null){
				Log.v("info", "安卓接受到了"+data);
				Log.e("error", "出错");
			}
			Log.e("error", "安卓");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
}