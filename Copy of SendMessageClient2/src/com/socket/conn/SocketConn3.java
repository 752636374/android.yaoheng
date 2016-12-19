package com.socket.conn;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
//import java.io.PrintStream;
import java.net.Socket;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.config.Config;
import com.mysql.conn.MySqlConn;

public class SocketConn3 implements Runnable {
	private OutputStream outputStream;
	StringBuilder s1, s2, s3;
	private ArrayList<String> string1, string2, string3;
	private String[] arr1, arr2, arr3;
	private int a1, a2, a3;
	int mydatatag_FETALPACKET=0;
	MySqlConn mySqlConn = new MySqlConn();
	InetAddress ia=null;
	public SocketConn3() {
		// TODO Auto-generated constructor stub
		
	}

	public void skget() {
		try {
			Socket socket = new Socket(Config.host, Config.port);
			outputStream = socket.getOutputStream();
			output();
			socket.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void gettag_FETALPACKET(int data) throws SQLException{	
		s1 = mySqlConn.tag_FETALPACKET(data);
		arr1 = s1.toString().split(" ");
		string1 = new ArrayList<>();
		string1.addAll(Arrays.asList(arr1));
	}
	
	
	public void output() throws SQLException, InterruptedException, IOException {				
		s1 = mySqlConn.tag_FETALPACKET(mydatatag_FETALPACKET);
		arr1 = s1.toString().split(" ");
		string1 = new ArrayList<>();
		string1.addAll(Arrays.asList(arr1));
		while (true) {
			for (; a1 < string1.size(); a1++) {
				byte[] ss1 = new byte[] { 0x00, 0x00, 0x01 };
				int longl = ss1.length;
				String body = "XYA" + "|" + string1.get(a1);
				byte[] bodyBytes = body.getBytes();
				int bodyLength = bodyBytes.length;
				int socketLength = longl + 1 +bodyLength;
				int index = 0;
				byte[] soc = new byte[socketLength];				
				for (int i = 0; i < longl; i++) {
					soc[index++] = ss1[i];
				}
				byte num = (byte) (bodyLength );
				soc[index++] = num;
				for (int i = 0; i < bodyLength; i++) {
					soc[index++] = bodyBytes[i];
				}

				outputStream.write(soc);
				outputStream.flush();
				Thread.sleep(Config.time);
				if (a1 + 1 >= string1.size()) {
					gettag_FETALPACKET(mydatatag_FETALPACKET+10);
					mydatatag_FETALPACKET+=10;
					a1 = -1;
					if(mydatatag_FETALPACKET==300000){
						mydatatag_FETALPACKET=0;
					}
				}
			}
		}
	}

	public void start() {
		// TODO Auto-generated method stub
		skget();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
