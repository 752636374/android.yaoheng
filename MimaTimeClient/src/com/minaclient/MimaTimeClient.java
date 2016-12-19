package com.minaclient;

import com.datadeal.SocketConn;

public class MimaTimeClient {

	public static void main(String[] args) {
			SocketConn socketConn=new SocketConn();
			new Thread(socketConn).start();
		
		// 关闭
	}
}
