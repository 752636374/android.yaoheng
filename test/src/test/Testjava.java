package test;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Testjava {
	private static ServerSocket serverSocket;
	private static int port=9919;
	private static InputStream inputStream;
	static Socket socket=null;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		serverSocket=new ServerSocket(port);
		while(true){
			socket=serverSocket.accept();
			inputStream=socket.getInputStream();
			byte[] byte1=new byte[1024];
			while((inputStream.read(byte1))!=-1){
				String sbb=new String(byte1);
				System.out.println(sbb);
				byte1=new byte[1024];
			}
	}}
}
