package my_recive_demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class my_recive_demo {
	public static Socket socket;
	private static int port=8919;
	private static ServerSocket serverSocket;
	private static InputStream reader;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		serverSocket=new ServerSocket(port);
		byte[] data=new byte[1024];
		while(true){
			socket=serverSocket.accept();
			reader=socket.getInputStream();		
			while((reader.read(data))!=-1){
				for(int i1=0;i1<1024;i1++){
					if(data[i1]==0&&data[i1+1]==0&&data[i1+2]==0){
						int nlength=data[i1+3];
						byte[] data2=new byte[nlength];
						for(int j=0;j<nlength;j++){
							data2[j]=data[i1+4+j];
						}	
					}
				}
			}	
		}
	}
}
