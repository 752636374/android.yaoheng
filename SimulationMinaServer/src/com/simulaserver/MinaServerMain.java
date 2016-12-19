package com.simulaserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class MinaServerMain {
	private static InputStream inputStream;
	static ServerSocket serverSocket;
	static Reader reader;
	private static int port=9123;
	private static Socket socket;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		serverSocket=new ServerSocket(port);
		while(true){
			
			socket=serverSocket.accept();
			inputStream=socket.getInputStream();

			byte[] data=new byte[1024];
			System.out.println("你在哪里");
			while((inputStream.read(data))!=-1){
				System.out.println("有数据啦");
				String sb=new String(data);
				System.out.println(sb);
				data=new byte[1024];
			}
			if(socket.isClosed()){
				System.out.println("Socket已被关闭");
			}
		}
	}

}
