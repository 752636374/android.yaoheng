package com.playvideo;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;

import com.example.videoplay.ConfigPP;

import android.os.Environment;
import android.util.Log;

public class Play1 {
	private DataOutputStream write;
	private BufferedInputStream infile;
	private FileOutputStream outfile;
	private String myname;

	public Play1(String myname) {
		// TODO Auto-generated constructor stub
		this.myname = myname;
	}

	public void getvideo() {
		try {
			Socket socket = new Socket(ConfigPP.IP, ConfigPP.PORT);// Log.e("error","连接成功");
			write = new DataOutputStream(socket.getOutputStream());
			write.writeUTF("clientvideoquest++" + " " + myname);
			infile = new BufferedInputStream(socket.getInputStream());// Log.e("error","连接-输入建立");															
			String dataPath = Environment.getExternalStorageDirectory() + File.separator + "amyvideo" + File.separator
					+ "aTest_Movie.mp4";
			outfile = new FileOutputStream(dataPath);
			int len;
			byte[] data = new byte[1024*4];
			while ((len = infile.read(data)) != -1) {
				outfile.write(data, 0, len);
				data = new byte[1024*4];
			}
			Log.e("error", "连接-接收完毕"+"/接收地址"+"--"+dataPath);
			write.close();
			infile.close();
			outfile.close();
			
			socket.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
