package com.send.message;
//客户端

import java.sql.SQLException;
import com.socket.conn.SocketConn3;

public class SendMessage {

	public SendMessage() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		while(true){
					SocketConn3[] s=new SocketConn3[5];
					for(int i=0;i<5;i++){
						s[i]=new SocketConn3();
//						System.out.println(i);
						s[i].start();						
					}
		}
	}
}
