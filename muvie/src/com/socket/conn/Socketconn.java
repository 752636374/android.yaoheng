package com.socket.conn;
import java.io.DataInputStream;
//服务端
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import com.socket.data.Skserver;
import com.socket.login.MyLoginServer;
import com.socket.output.SendVideo;
import com.socket.output.Skoutput;

public class Socketconn extends Thread{
	public Socket socket;
	private int port=8919;
	private ServerSocket serverSocket;
	private DataInputStream reader;
	public Socketconn() {
		// TODO Auto-generated constructor stub
		super();
	}
	public void getconn() throws IOException, SQLException, InterruptedException{
		serverSocket=new ServerSocket(port);
		while(true){
			sleep(500);
			socket=serverSocket.accept();
			reader=new DataInputStream(socket.getInputStream());
			String mes=reader.readUTF();
			if(mes.contains("clientsend++")){
				Skserver skserver=new Skserver(socket);
				System.out.println("接收++服务器开始处理");
				skserver.start();
				serverSocket.close();
			}
			if(mes.contains("clientreceive++")){
				Skoutput skoutput=new Skoutput(socket);
				System.out.println("发送++服务器开始处理");
				skoutput.start();
			}
			if(mes.contains("IAMLOGIN++")){
				MyLoginServer myLoginServer=new MyLoginServer(socket);
				myLoginServer.setMes(mes);
				System.out.println("验证++服务器开始处理");
				Thread t = new Thread(myLoginServer);
				t.start();
			}
			if(mes.contains("clientvideoquest++")){
				SendVideo sendVideo=new SendVideo(socket,mes);
				System.out.println("video发送++服务器开始处理");
				Thread t = new Thread(sendVideo);
				t.start();
			}
		}
	}
	public void run(){
		try {
			getconn();
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