package com.main.muvie;
//服务端
import java.io.IOException;
import java.sql.SQLException;
import com.socket.conn.Socketconn;

public class MuvieRun {
	public MuvieRun() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException, SQLException {
		// TODO Auto-generated method stub
		Socketconn socketconn=new Socketconn();
		socketconn.start();
		
	}
}
