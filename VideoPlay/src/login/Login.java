package login;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.example.videoplay.ConfigPP;
import com.example.videoplay.MainActivity;
import com.example.videoplay.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	private EditText usernameET, passwordET;
	private Button loginButton, registerButton;
	private String s = null;
	private static int FLAG = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		findV();
		myregisteronclick();
		myloginonclick();
	}

	public void findV() {
		usernameET = (EditText) findViewById(R.id.usernameET);
		passwordET = (EditText) findViewById(R.id.passwordET);
		loginButton = (Button) findViewById(R.id.loginButton);
		registerButton = (Button) findViewById(R.id.registerButtton);
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Thread t = Thread.currentThread();
			String name = t.getName();
			Log.d("debug", "发送——HANDLER3" + name);
			switch (msg.what) {

			case 1:// 自己业务
				s = "yes";
				Log.d("debug", "发送——赋值完成");
				FLAG = 1;
				break;
			case 2:
				s = "no";
				Log.d("debug", "发送——赋值完成o");
				FLAG = 1;
				break;
			case 3:
				s = "nowifi";
				Log.d("debug", "发送——赋值完成c");
				FLAG = 1;
				break;
			default:
				break;
			}
			

			while (FLAG == 1) {
				Log.d("debug", "发送-开始判断");
				FLAG = 0;
				if (s.equals("yes")) {
					s = null;
					Intent intent = new Intent();
					intent.setClass(Login.this, MainActivity.class);
					startActivity(intent);
				} else if (s.equals("no")) {
					s = null;
					Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
				} else if (s.equals("nowifi")) {
					s = null;
					Toast.makeText(getApplicationContext(), "请检查是否联网", Toast.LENGTH_SHORT).show();
				}
			}
		}
	};

	class myRunnable implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Thread t = Thread.currentThread();
			String name = t.getName();
			Log.d("debug", "发送——线程名称2" + name);
			String ss = "abc";
			Socket socket;
			try {
				socket = new Socket(ConfigPP.IP, ConfigPP.PORT);
				InputStream inputStream = socket.getInputStream();
				DataOutputStream dataoutputStream = new DataOutputStream(socket.getOutputStream());
				dataoutputStream.writeUTF("IAMLOGIN++" + "/" + getusernameET() + "/" + getpasswordET());
				Log.d("debug", "发送消息");
				byte data[] = new byte[3];
				int len, length = 0;
				while (((len = inputStream.read(data)) != -1)) {
					for (int i = 0; i < data.length; i++) {
						if (data[i] != 0) {
							length++;
						}
					}
					byte mydata[] = new byte[length];
					for (int i = 0; i < length; i++) {
						mydata[i] = data[i];
					}
					ss = new String(mydata);
					Log.d("debug", "发送——接收消息" + ss);
				}
				socket.close();
				Log.d("debug", "发送——关闭socket");
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message message = new Message();
			if (ss.equals("yes")) {
				message.what = 1;
				Log.d("debug", "发送- 验证为yes");
			}
			if (ss.equals("no")) {
				message.what = 2;
				Log.d("debug", "发送- 验证为no");
			}
			if (ss.equals("abc")) {
				message.what = 3;
				Log.d("debug", "发送 -验证为no");
			}
			mHandler.sendMessage(message);

		}
	}

	public void myloginonclick() {
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Thread thread = new Thread(new myRunnable());
				thread.start();
			}
		});
	}

	public void myregisteronclick() {
		registerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "无法注册", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public Editable getusernameET() {
		Editable username = usernameET.getText();
		return username;

	}

	public Editable getpasswordET() {
		Editable password = passwordET.getText();
		return password;

	}

}
