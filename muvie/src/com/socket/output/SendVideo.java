package com.socket.output;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

import com.music.mysql.Mysqlcon;

public class SendVideo implements Runnable {
	File file;
	FileInputStream in;
	BufferedOutputStream out;
	Socket socket = null;
	String mes = null;

	public void output() throws IOException, SQLException {
		Mysqlcon mysqlcon = new Mysqlcon();
		System.out.println("发送电影");
		mysqlcon.setMes(mes);
		String s = mysqlcon.getdataaddress();
		file = new File(s);
		System.out.println("地址-"+s);
		in=new FileInputStream(file);
		int len;
		byte[] data = new byte[1024*4];
		out = new BufferedOutputStream(socket.getOutputStream());
		while((len=in.read(data))>0){
			System.out.println("发送数据");
			out.write(data,0,len);
			data = new byte[1024];
		}
		out.close();
		socket.close();
	}

	public void run() {
		try {
			output();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public SendVideo(Socket socket,String mes) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		this.mes = mes;
	}
}
