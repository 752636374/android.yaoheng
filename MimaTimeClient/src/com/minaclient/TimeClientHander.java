package com.minaclient;

import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoHandler;
import org.apache.mina.common.IoSession;

import com.datadeal.SocketConn;

public class TimeClientHander implements IoHandler {

	@Override
	public void exceptionCaught(IoSession arg0, Throwable arg1) throws Exception {
		// TODO Auto-generated method stub
		arg1.printStackTrace();
	}

	@Override
	public void messageReceived(IoSession arg0, Object message) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("client接受信息:"+message.toString());
	}

	@Override
	public void messageSent(IoSession arg0, Object message) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("client发送信息"+message.toString());
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("client与:"+session.getRemoteAddress().toString()+"断开连接");
		SocketConn socketConn=new SocketConn();
		Thread thread=new Thread(socketConn);
		thread.start();
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("client与:"+session.getRemoteAddress().toString()+"建立连接");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		// TODO Auto-generated method stub
		System.out.println( "IDLE " + session.getIdleCount( status ));
	}

	@Override
	public void sessionOpened(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("打开连接");
	}

	
}
