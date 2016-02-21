package com.eastflag.game.adaptor.tcpc.server;

import java.net.SocketAddress;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class TcpServer {
	private final Logger logger = LoggerFactory.getLogger(TcpServer.class.getName());
	
	private int bossgroupSize;
	private int workergroupSize;
	private TcpServerHandler tcpServerHandler;
	private FMPServerHandler fmpServerHandler;
	private int port;
	
	public TcpServer() {
		bossgroupSize = 10;
		workergroupSize = 10;
		tcpServerHandler = new TcpServerHandler();
		fmpServerHandler = new FMPServerHandler();
		port = 9999;
	}

	public void start() {
		NioEventLoopGroup bossGroup = new NioEventLoopGroup(bossgroupSize);
		NioEventLoopGroup workerGroup = new NioEventLoopGroup(workergroupSize);
        
		ServerBootstrap bootStrap = new ServerBootstrap();
    	bootStrap.group(bossGroup, workerGroup)
         .channel(NioServerSocketChannel.class)
         .childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
//				ch.pipeline().addLast(
//                		new ObjectEncoder(),
//                		new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
//                		tcpServerHandler);
				
//				ch.pipeline().addLast("MsgEncoder", new MsgEncoder());
//    			ch.pipeline().addLast("MsgDecoder", new MsgDecoder());
//    			ch.pipeline().addLast("SmsHandler", tcpServerHandler);
    			
    			
    			ChannelPipeline pipeline = ch.pipeline();

    		    // Add the text line codec combination first,
    		    pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
    		    
    		    // the encoder and decoder are static as these are sharable
    		    pipeline.addLast("decoder", new StringDecoder());
    		    pipeline.addLast("encoder", new StringEncoder());

    		    // and then business logic.
    		    pipeline.addLast("handler", fmpServerHandler);
			}
		});
    	    	
    	logger.info("## Server running...1");
    	
		try {
			// Start the server.
	        ChannelFuture f = bootStrap.bind(port).sync();
	        logger.info("Ticket Symbol Server started");

	        // Wait until the server socket is closed.
	        //f.channel().closeFuture().sync();
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
