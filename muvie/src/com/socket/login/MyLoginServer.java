package com.socket.login;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class MyLoginServer implements Runnable {
	OutputStream outputStream;
	Socket socket = null;
	String mes = null;
	String s1, s2;

	public MyLoginServer(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public void output() throws ClassNotFoundException, SQLException, IOException {
		String[] ss = mes.split("/");	//获取数据分割
		if(mes!=null){
				s1 = ss[1];
				s2 = ss[2];
			
		}
		MyMysqlSelect myMysqlSelect = new MyMysqlSelect();
		StringBuilder username = myMysqlSelect.selectusername();
		outputStream=socket.getOutputStream();
		String s=username.toString();
		String[] sb=s.split("/");		//数据库数据分割
		
		System.out.println(username+" "+mes);
		String syes="yes";
		String sno="no";
		byte[] datano=sno.getBytes();
		byte[] data=syes.getBytes();
		if(sb[0].equals(ss[1])&&sb[1].equals(ss[2])){
			outputStream.write(data);
			System.out.println("OK");
		}
		else {
			outputStream.write(datano);
		}
		outputStream.close();
	}

	public class MyMysqlSelect {
		private String driver = "com.mysql.jdbc.Driver";
		private String url = "jdbc:mysql://localhost:3306/video_play";
		private String username = "root";
		private String password = "";
		private String sqlusername = "select * from user_username_password";
		private Connection conn = null;
		private PreparedStatement preparedStatement;
		private StringBuilder builder;

		public StringBuilder selectusername() throws ClassNotFoundException, SQLException {
			Class.forName(driver);
			conn = (Connection) DriverManager.getConnection(url,username,password);
			builder = new StringBuilder();
			preparedStatement = (PreparedStatement) conn
					.prepareStatement(sqlusername + " where username=" + "'" + s1 + "'");
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			int res = resultSet.getMetaData().getColumnCount();
			while (resultSet.next()) {
				for (int i = 1; i <= res; i++) {
					builder.append(resultSet.getString(i) + "/");
					System.out.println("查询完毕");
				}
			}
			String sss=builder.toString();
			int i=sss.length();
			System.out.println(i);
			return builder;
			
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			output();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
