package com.mysqlconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlConn{
	public MySqlConn() {
		// TODO Auto-generated constructor stub
	}
	 String driver="com.mysql.jdbc.Driver";
	 String url="jdbc:mysql://localhost:3306/fetal_heart_monitor";
	 String username="root";
	 String password="";
	 Connection conn=null;
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
	public StringBuilder the_fetal_movement() throws SQLException{
		Connection connection=getConn();
		String sql="select data from The_fetal_movement";
		PreparedStatement preparedStatement;
		StringBuilder builder=new StringBuilder();
		preparedStatement=(PreparedStatement) connection.prepareStatement(sql);
		ResultSet resultSet=preparedStatement.executeQuery();
		int res=resultSet.getMetaData().getColumnCount();
		while(resultSet.next()){
			for(int i=1;i<=res;i++){
				builder.append(resultSet.getString(i)+" ");				
			}
		}		
		return builder;	
	}
	public StringBuilder uterine_contraction() throws SQLException{
		Connection connection=getConn();
		String sql="select data from Uterine_contraction";
		PreparedStatement preparedStatement;
		StringBuilder builder=new StringBuilder();
		preparedStatement=(PreparedStatement) connection.prepareStatement(sql);
		ResultSet resultSet=preparedStatement.executeQuery();
		int res=resultSet.getMetaData().getColumnCount();
		while(resultSet.next()){
			for(int i=1;i<=res;i++){
				builder.append(resultSet.getString(i)+" ");				
			}
		}		
		return builder;	
	}
	public StringBuilder fetal_movement() throws SQLException{
		Connection connection=getConn();
		String sql="select data from fetal_movement";
		PreparedStatement preparedStatement;
		StringBuilder builder=new StringBuilder();
		preparedStatement=(PreparedStatement) connection.prepareStatement(sql);
		ResultSet resultSet=preparedStatement.executeQuery();
		int res=resultSet.getMetaData().getColumnCount();
		while(resultSet.next()){
			for(int i=1;i<=res;i++){
				builder.append(resultSet.getString(i)+" ");				
			}
		}		
		return builder;	
	}
	public StringBuilder heart_rate() throws SQLException{
		Connection connection=getConn();
		String sql="select data from heart_rate";
		PreparedStatement preparedStatement;
		StringBuilder builder=new StringBuilder();
		preparedStatement=(PreparedStatement) connection.prepareStatement(sql);
		ResultSet resultSet=preparedStatement.executeQuery();
		int res=resultSet.getMetaData().getColumnCount();
		while(resultSet.next()){
			for(int i=1;i<=res;i++){
				builder.append(resultSet.getString(i)+" ");				
			}
		}		
		return builder;	
	}
	public StringBuilder non_invasive_blood_pressure() throws SQLException{
		Connection connection=getConn();
		String sql="select data from Non_invasive_blood_pressure";
		PreparedStatement preparedStatement;
		StringBuilder builder=new StringBuilder();
		preparedStatement=(PreparedStatement) connection.prepareStatement(sql);
		ResultSet resultSet=preparedStatement.executeQuery();
		int res=resultSet.getMetaData().getColumnCount();
		while(resultSet.next()){
			for(int i=1;i<=res;i++){
				builder.append(resultSet.getString(i)+" ");	
			}
		}		
		return builder;	
	}
	public StringBuilder oxygen() throws SQLException{
		Connection connection=getConn();
		String sql="select data from Oxygen";
		PreparedStatement preparedStatement;
		StringBuilder builder=new StringBuilder();
		preparedStatement=(PreparedStatement) connection.prepareStatement(sql);
		ResultSet resultSet=preparedStatement.executeQuery();
		int res=resultSet.getMetaData().getColumnCount();
		while(resultSet.next()){
			for(int i=1;i<=res;i++){
				builder.append(resultSet.getString(i)+" ");				
			}
		}		
		return builder;	
	}
	public StringBuilder pulse_rate() throws SQLException{
		Connection connection=getConn();
		String sql="select data from Pulse_rate";
		PreparedStatement preparedStatement;
		StringBuilder builder=new StringBuilder();
		preparedStatement=(PreparedStatement) connection.prepareStatement(sql);
		ResultSet resultSet=preparedStatement.executeQuery();
		int res=resultSet.getMetaData().getColumnCount();
		while(resultSet.next()){
			for(int i=1;i<=res;i++){
				builder.append(resultSet.getString(i)+" ");				
			}
		}		
		return builder;	
	}
	public StringBuilder respiration_rate() throws SQLException{
		Connection connection=getConn();
		String sql="select data from Respiration_rate";
		PreparedStatement preparedStatement;
		StringBuilder builder=new StringBuilder();
		preparedStatement=(PreparedStatement) connection.prepareStatement(sql);
		ResultSet resultSet=preparedStatement.executeQuery();
		int res=resultSet.getMetaData().getColumnCount();
		while(resultSet.next()){
			for(int i=1;i<=res;i++){
				builder.append(resultSet.getString(i)+" ");				
			}
		}		
		return builder;	
	}
	public StringBuilder temperature() throws SQLException{
		Connection connection=getConn();
		String sql="select data from temperature";
		PreparedStatement preparedStatement;
		StringBuilder builder=new StringBuilder();
		preparedStatement=(PreparedStatement) connection.prepareStatement(sql);
		ResultSet resultSet=preparedStatement.executeQuery();
		int res=resultSet.getMetaData().getColumnCount();
		while(resultSet.next()){
			for(int i=1;i<=res;i++){
				builder.append(resultSet.getString(i)+" ");				
			}
		}		
		return builder;	
	}

	
}

