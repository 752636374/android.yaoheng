package com.mysql.conn;
//客户端
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.config.Config;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class MySqlConn{
	public MySqlConn() {
		// TODO Auto-generated constructor stub
	}
	 String driver="com.mysql.jdbc.Driver";
	 String url=Config.url;
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
	
	
	//二次模拟
	
	//

	public StringBuilder tag_FETALPACKET(int j) throws SQLException{
		Connection connection=getConn();
		String sql="select * from tag_FETALPACKET limit "+j+","+(j+10);
		PreparedStatement preparedStatement;
		StringBuilder builder=new StringBuilder();
		preparedStatement=(PreparedStatement) connection.prepareStatement(sql);
		ResultSet resultSet=preparedStatement.executeQuery();
		int res=resultSet.getMetaData().getColumnCount();
		while(resultSet.next()){
			for(int i=1;i<=res;i++){
				builder.append(resultSet.getString(i)+"|");				
			}
			builder.append(" ");
		}		
		return builder;	
	}
	
}
