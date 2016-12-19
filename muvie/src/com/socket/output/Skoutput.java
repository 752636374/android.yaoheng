package com.socket.output;
//服务端
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.SQLException;
import com.music.mysql.Mysqlcon;

public class Skoutput extends Thread {
	private PrintStream pStream1;
	Socket socket=null;
	public Skoutput(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket=socket;
	}
	public void output() throws IOException, SQLException, InterruptedException{
			
			System.out.println("服务器准备2");
			Mysqlcon mysqlcon=new Mysqlcon();
			String s=mysqlcon.getdata();	
			pStream1=new PrintStream(socket.getOutputStream());
			pStream1.println(s);
			pStream1.flush();
			pStream1.close();

	}
	public void run(){
		try {
			output();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
