package com.socket.conn;

//客户端
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//import java.io.PrintStream;
import java.net.Socket;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.mysql.conn.MySqlConn;

public class SocketConn implements Runnable {
	private OutputStream outputStream;
	StringBuilder s1, s2, s3;
	private ArrayList<String> string1, string2, string3;
	private String[] arr1, arr2, arr3;
	private int a1, a2, a3;

	public SocketConn() {
		// TODO Auto-generated constructor stub

	}

	public void skget() {
		try {
			int port = 8919;
			String host = "192.168.0.5";
			Socket socket = new Socket(host, port);
			outputStream = socket.getOutputStream();
			output();
			socket.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void output() throws SQLException, InterruptedException, IOException {
		MySqlConn mySqlConn = new MySqlConn();

		s1 = mySqlConn.tag_FETALPACKET();
		s2 = mySqlConn.tag_ETCOPACKET();
		s3 = mySqlConn.tag_BPBOPACEKT();
		

		arr1 = s1.toString().split(" ");
		arr2 = s2.toString().split(" ");
		arr3 = s3.toString().split(" ");
		

		string1 = new ArrayList<>();
		string2 = new ArrayList<>();
		string3 = new ArrayList<>();
		

		string1.addAll(Arrays.asList(arr1));
		string2.addAll(Arrays.asList(arr2));
		string3.addAll(Arrays.asList(arr3));
		

		while (true) {
			for (; a1 < string1.size() && a2 < string2.size() && a3 < string3.size(); a1++, a2++, a3++ ) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");// 设置日期格式
				String time = df.format(new Date());
				byte[] bytetime = time.getBytes();
				int bytenum = bytetime.length;
				byte[] ss1=new byte[]{0x00,0x00,0x01};
				int longl=ss1.length;
				
				String body = string1.get(a1);
				String body2=string2.get(a2);
				String body3=string3.get(a3);
				
				byte[] bodyBytes = body.getBytes();
				byte[] bodyBytes2 = body2.getBytes();
				byte[] bodyBytes3 = body3.getBytes();
					
				int bodyLength = bodyBytes.length;
				int bodyLength2 = bodyBytes2.length;
				int bodyLength3 = bodyBytes3.length;
				
				
				int socketLength = longl + 1 + bodyLength + bytenum;
				int socketLength2 = longl + 1 + bodyLength2 + bytenum;
				int socketLength3 = longl + 1 + bodyLength3 + bytenum;
				
				
				int index = 0;
				int index2 = 0;
				int index3 = 0;
				
				
				byte[] soc = new byte[socketLength];
				byte[] soc2 = new byte[socketLength2];
				byte[] soc3 = new byte[socketLength3];
				
				
				for(int i=0;i<longl;i++){
					soc[index++]=ss1[i];
					soc2[index2++]=ss1[i];
					soc3[index3++]=ss1[i];
				}
					
					byte num=(byte) (bodyLength+bytenum);
					byte num2=(byte) (bodyLength2+bytenum);
					byte num3=(byte) (bodyLength3+bytenum);
					soc[index++] = num;
					soc2[index2++] = num2;
					soc3[index3++] = num3;
				
				
				
				for (int i = 0; i < bodyLength; i++) {
					soc[index++] = bodyBytes[i];
				}
				for (int i = 0; i < bodyLength2; i++) {
					soc2[index2++] = bodyBytes2[i];
				}
				for (int i = 0; i < bodyLength3; i++) {
					soc3[index3++] = bodyBytes3[i];
				}
				
				for (int i = 0; i < bytenum; i++) {
					soc[index++] = bytetime[i];
					soc2[index2++] = bytetime[i];
					soc3[index3++] = bytetime[i];					
					// System.out.println("on ");
				}

				
				outputStream.write(soc);	
				outputStream.flush();
				Thread.sleep(500);
				
				outputStream.write(soc2);	
				outputStream.flush();
				Thread.sleep(500);
				
				outputStream.write(soc3);	
				outputStream.flush();
				Thread.sleep(500);
				if (a1 + 1 >= string1.size()) {
					a1 = -1;
				}
				if (a2 + 1 >= string2.size()) {
					a2 = -1;
				}
				if (a3 + 1 >= string3.size()) {
					a3 = -1;
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
