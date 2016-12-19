package com.socket.data;
//服务端
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.text.NumberFormat;


public class Skserver extends Thread{
	private InputStream inputStream;
	private OutputStream outputStream;
	Socket socket=null;
	StringBuilder stringBuilder=new StringBuilder();
	long i1=0;
	public Skserver(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket=socket;
	}
	
	public void server() throws IOException, SQLException, InterruptedException{
//			System.out.println("我来了");
			inputStream=socket.getInputStream();
			outputStream=socket.getOutputStream();
			byte[] byte1=new byte[1024];
			while((inputStream.read(byte1))!=-1){
				String sbb=new String(byte1);
				System.out.println(sbb);
				
				int index=3;
				//获取发送时的数据长度
				byte[] ssss=new byte[8];
				for(int i=0;i<8;i++){
					ssss[i]=byte1[index++];
				}
				String sssss=new String(ssss);
				
				//计算实际获取长度
				int length=0;
				for(;byte1[length]!=0&&length<1023;){
					length++;
				}
				
				//验证获取到的长度与实际长度是否相等
				NumberFormat numberFormat = NumberFormat.getNumberInstance();
				numberFormat.setMinimumIntegerDigits(8);
		        numberFormat.setGroupingUsed(false);
		        byte [] num = numberFormat.format(length).getBytes();
		        String numString=new String(num);		        
		        if(numString.equals(sssss)){
//		        	System.out.println("I AM RIGHT");	
		        }
		        else {
		        	System.out.println(numString);
		        	System.out.println("任务出错");
					outputStream.write(00000001111001001);
					outputStream.flush();
					byte1=new byte[1024];
					continue;
				}
		        //获取实际有效数据
				int bodylength=11;
				byte[] body=new byte[length-11];
				for(int i=0;i<(length-11);i++){
					body[i]=byte1[bodylength++];
				}
				//数组清空

				byte1=new byte[1024];
				
			}	
	}

	
	public void run(){
			try {
				server();
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
