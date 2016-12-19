package sockettest;

import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketTest {

	public SocketTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StringBuilder builder;
		List<String> data;
		DataOutputStream write;
		try {
			data=new ArrayList<String>();
			int port=8919;
			String host="192.168.0.24";
			Socket socket=new Socket(host,port);
			write=new DataOutputStream(socket.getOutputStream());
			write.writeUTF("clientreceive++");
			InputStreamReader reader2=new InputStreamReader(socket.getInputStream());
			char chars[]=new char[1024];
			int len;
			builder=new StringBuilder();
			while((len=reader2.read(chars))!=-1){
				builder.append(chars,0,len);
				System.out.println(builder);
			}					
			socket.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
