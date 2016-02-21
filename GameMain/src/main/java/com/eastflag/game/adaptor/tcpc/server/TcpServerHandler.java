package com.eastflag.game.adaptor.tcpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eastflag.game.adaptor.tcpc.TcpServerAdaptor;
import com.eastflag.game.adaptor.tcpc.message.RawMessage;
import com.eastflag.game.core.message.TcpMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TcpServerHandler extends ChannelInboundHandlerAdapter {
	private final Logger logger = LoggerFactory.getLogger(TcpServerHandler.class.getName());
	
	private TcpServerAdaptor adaptor;
	
	public TcpServerHandler(TcpServerAdaptor adaptor) {
		super();
		this.adaptor = adaptor;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
		
		logger.debug("## Channel Active...");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		super.channelRead(ctx, msg);
		
		RawMessage rawMsg = (RawMessage)msg;
		
		
		// Message Type 에 따라 거른다.
		
		
		
		
		
		
		
		// Call 을 만든다.
		TcpMessage tcpMessage = makeTcpMessage(rawMsg);
		
		
		// dispatch 한다.
		adaptor.dispatchMessage(tcpMessage);
	}

	private TcpMessage makeTcpMessage(RawMessage rawMsg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
		
		logger.error("## exceptionCaught...", cause);
	}

	public TcpServerHandler() {
		// TODO Auto-generated constructor stub
	}

}
