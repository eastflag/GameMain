package com.eastflag.game.adaptor.websocketc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class WebSocketServer {
	
	private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class.getName());
	private static final int PORT = Integer.parseInt(System.getProperty("port", "7777"));
	
	private int bossgroupSize;
	private int workergroupSize;

	public WebSocketServer() {
		bossgroupSize = 1;
		workergroupSize = 10;
	}
	
	public void start() {
		NioEventLoopGroup bossGroup = new NioEventLoopGroup(bossgroupSize);
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup)
						.channel(NioServerSocketChannel.class)
						.handler(new LoggingHandler(LogLevel.INFO))
						.childHandler(new ChannelInitializer<SocketChannel>() {

							@Override
							protected void initChannel(SocketChannel ch) throws Exception {
								ChannelPipeline p = ch.pipeline();
								p.addLast("encoder", new HttpResponseEncoder());
								p.addLast("decoder", new HttpRequestDecoder());
								p.addLast("aggregator", new HttpObjectAggregator(65536));
								p.addLast("handler", new StockTickerServerHandler());
							}
						});
			
			// Start the server.
			ChannelFuture f = bootstrap.bind(PORT).sync();
	        logger.info("Ticket Symbol Server started");

	        // Wait until the server socket is closed.
	        // 풀게 되면 메인쓰레드가 멈춘다.
	        //f.channel().closeFuture().sync();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			logger.info("Ticket Symbol Server shutdown started");
			// Shut down all event loops to terminate all threads.
			// 풀게 되면, 시작과 동시에 종료된다.
			//bossGroup.shutdownGracefully();
			//workerGroup.shutdownGracefully();
			logger.info("Ticket Symbol Server shutdown completed");
		}
	}

}
