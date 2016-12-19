package com.example.videoplay;

import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class SocketClient{
	StringBuilder builder;
	List<String> data;
	DataOutputStream write;
	public void run(){
		try {
			data=new ArrayList<String>();
			Log.e("error", "开始连接");
			Socket socket=new Socket(ConfigPP.IP,ConfigPP.PORT);
			Log.e("error", "连接成功");
			write=new DataOutputStream(socket.getOutputStream());
			write.writeUTF("clientreceive++");
			
			InputStreamReader reader2=new InputStreamReader(socket.getInputStream());
			char chars[]=new char[1024];
			int len;
			builder=new StringBuilder();
			while((len=reader2.read(chars))!=-1){
				builder.append(chars,0,len);
				data.add(builder.toString());				
			}					
			socket.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
