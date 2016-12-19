package com.minaserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.prefixedstring.PrefixedStringCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.config.Config;
import com.handler.TimeServerHandler;

public class MinaServer {
	private static final int PORT = Config.PORT;

	public static void main(String[] args) throws IOException {
			IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new PrefixedStringCodecFactory(Charset.forName("UTF-8"))));
		acceptor.setHandler(new TimeServerHandler());
		
		acceptor.getSessionConfig().setReadBufferSize(20480);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

		acceptor.bind(new InetSocketAddress(PORT));
		
		

	}
}
