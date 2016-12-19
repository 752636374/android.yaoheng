package com.music.mysql;
//服务端
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class Mysqlcon {
	 static String driver="com.mysql.jdbc.Driver";
	 String url="jdbc:mysql://localhost:3306/video_play";
	 static String username="root";
	 static String password="";
	 static Connection conn=null;
	 String sqlname="select name from video_data";
	 String sqladdress="select address from video_data where name=";
	 public String mes;
	public Mysqlcon() {
		// TODO Auto-generated constructor stub

	}
	public  Connection getConn(){
		try {
			Class.forName(driver);
			conn=(Connection) DriverManager.getConnection(url,username,password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public  String getdata() throws SQLException{
		Connection connection=getConn();
		PreparedStatement preparedStatement;
		String s ="";
		preparedStatement=(PreparedStatement) connection.prepareStatement(sqlname);
		ResultSet resultSet=preparedStatement.executeQuery();
		int res=resultSet.getMetaData().getColumnCount();
		while(resultSet.next()){
			for(int i=1;i<=res;i++){
				s+=(resultSet.getString(i)+" ");
			}
		}		
		return s;		
	}
	
	public  String getdataaddress() throws SQLException{
		
		String[] mys=mes.split(" ");
		String sname=mys[1];
		
		Connection connection=getConn();
		PreparedStatement preparedStatement;
		String s ="";
		System.out.println(sname);
		preparedStatement=(PreparedStatement) connection.prepareStatement(sqladdress+"'"+sname+"'");
		ResultSet resultSet=preparedStatement.executeQuery();
		int res=resultSet.getMetaData().getColumnCount();
		while(resultSet.next()){
			for(int i=1;i<=res;i++){
				s+=(resultSet.getString(i));
			}
		}		
		return s;		
	}
	
	public void setMes(String mes) {
		this.mes = mes;
	}
}
