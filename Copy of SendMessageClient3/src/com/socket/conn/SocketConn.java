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
	private DataOutputStream write;
	private OutputStream outputStream;
	private InputStream inputStream;
	StringBuilder s1, s2, s3, s4, s5, s6, s7, s8, s9;
	private ArrayList<String> string1, string2, string3, string4, string5, string6, string7, string8, string9;
	private String[] arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9;
	private int a1, a2, a3, a4, a5, a6, a7, a8, a9;;

	public SocketConn() {
		// TODO Auto-generated constructor stub

	}

	public void skget() {
		try {
			int port = 1986;
			String host = "192.168.0.28";
			Socket socket = new Socket(host, port);
			write = new DataOutputStream(socket.getOutputStream());
			write.writeUTF("clientsend++");
			outputStream = socket.getOutputStream();
			inputStream = socket.getInputStream();
			output();
			socket.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void output() throws SQLException, InterruptedException, IOException {
		MySqlConn mySqlConn = new MySqlConn();

		s1 = mySqlConn.the_fetal_movement();
		s2 = mySqlConn.uterine_contraction();
		s3 = mySqlConn.fetal_movement();
		s4 = mySqlConn.heart_rate();
		s5 = mySqlConn.non_invasive_blood_pressure();
		s6 = mySqlConn.oxygen();
		s7 = mySqlConn.pulse_rate();
		s8 = mySqlConn.respiration_rate();
		s9 = mySqlConn.temperature();

		arr1 = s1.toString().split(" ");
		arr2 = s2.toString().split(" ");
		arr3 = s3.toString().split(" ");
		arr4 = s4.toString().split(" ");
		arr5 = s5.toString().split(" ");
		arr6 = s6.toString().split(" ");
		arr7 = s7.toString().split(" ");
		arr8 = s8.toString().split(" ");
		arr9 = s9.toString().split(" ");

		string1 = new ArrayList<>();
		string2 = new ArrayList<>();
		string3 = new ArrayList<>();
		string4 = new ArrayList<>();
		string5 = new ArrayList<>();
		string6 = new ArrayList<>();
		string7 = new ArrayList<>();
		string8 = new ArrayList<>();
		string9 = new ArrayList<>();

		string1.addAll(Arrays.asList(arr1));
		string2.addAll(Arrays.asList(arr2));
		string3.addAll(Arrays.asList(arr3));
		string4.addAll(Arrays.asList(arr4));
		string5.addAll(Arrays.asList(arr5));
		string6.addAll(Arrays.asList(arr6));
		string7.addAll(Arrays.asList(arr7));
		string8.addAll(Arrays.asList(arr8));
		string9.addAll(Arrays.asList(arr9));

		while (true) {
			for (; a1 < string1.size() && a2 < string2.size() && a3 < string3.size() && a4 < string4.size()
					&& a5 < string5.size() && a6 < string6.size() && a7 < string7.size() && a8 < string8.size()
					&& a9 < string9.size(); a1++, a2++, a3++, a4++, a5++, a6++, a7++, a8++, a9++) {
				// System.out.println("on 够够够");

				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
				String time = df.format(new Date());
				byte[] bytetime = time.getBytes();
				int bytenum = bytetime.length;
				// System.out.println(df.format(new Date()));// new
				// Date()为获取当前系统时间
				String s="XYC";
				byte[] ss1=s.getBytes();
				int longl=ss1.length;
				
				String body = string1.get(a1) + "/" + string2.get(a2) + "/" + string3.get(a3) + "/" + string4.get(a4)
						+ "/" + string5.get(a5) + "/" + string6.get(a6) + "/" + string7.get(a7) + "/" + string8.get(a8)
						+ "/" + string9.get(a9);
				byte[] bodyBytes = body.getBytes();
				int bodyLength = bodyBytes.length;
				int socketLength = longl + 8 + bodyLength + bytenum;
				int index = 0;
				byte[] soc = new byte[socketLength];
				
				
				for(int i=0;i<longl;i++){
					soc[index++]=ss1[i];
				}
				NumberFormat numberFormat = NumberFormat.getNumberInstance();
				numberFormat.setMinimumIntegerDigits(8);
				numberFormat.setGroupingUsed(false);
				byte[] num = numberFormat.format(socketLength).getBytes();
				for (int i = 0; i < 8; i++) {
					soc[index++] = num[i];
				}
				for (int i = 0; i < bodyLength; i++) {
					soc[index++] = bodyBytes[i];
				}
				// System.out.println(bytenum);

				for (int i = 0; i < bytenum; i++) {
					soc[index++] = bytetime[i];
					// System.out.println("on ");
				}

				
				outputStream.write(soc);		
				if (inputStream.available() != 0) {
					if (inputStream.read() == 00000001111001001) {
						outputStream.write(soc);
					}
				}

				if (a1 + 1 >= string1.size()) {
					a1 = -1;
				}
				if (a2 + 1 >= string2.size()) {
					a2 = -1;
				}
				if (a3 + 1 >= string3.size()) {
					a3 = -1;
				}
				if (a4 + 1 >= string4.size()) {
					a4 = -1;
				}
				if (a5 + 1 >= string5.size()) {
					a5 = -1;
				}
				if (a6 + 1 >= string6.size()) {
					a6 = -1;
				}
				if (a7 + 1 >= string7.size()) {
					a7 = -1;
				}
				if (a8 + 1 >= string8.size()) {
					a8 = -1;
				}
				if (a9 + 1 >= string9.size()) {
					a9 = -1;
				}

				// String sos=new String(soc);
				// System.out.println(soc[0]+" "+soc[1]+sos);
				outputStream.flush();
				Thread.sleep(50);

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
