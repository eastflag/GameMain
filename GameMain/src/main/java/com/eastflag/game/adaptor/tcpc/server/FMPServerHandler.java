package com.eastflag.game.adaptor.tcpc.server;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eastflag.game.adaptor.tcpc.vo.UserState;
import com.google.gson.Gson;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class FMPServerHandler extends SimpleChannelInboundHandler<String> {
	private final Logger logger = LoggerFactory.getLogger(FMPServerHandler.class.getName());
  
	private Map<String, UserState> cacheUser;
	private Gson gson;
  
  
	public FMPServerHandler() {
		gson = new Gson();
	}
	
  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
  	// As soon as possible, have to get reference of appContext. and next, have to get reference of handler.
  	//handler = BeanGetter.getInstance().getSampleHandler();
  	
  	// Send greeting for a new connection.
  	//ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
  	//ctx.write("It is " + new Date() + " now.\r\n");
  	//ctx.flush();
	  logger.debug("## Channel Active...");
  }
	
  	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String request) throws Exception {
		// 	Generate and write a response.
		String response;
      
      boolean close = false;
      if (request.isEmpty()) {
          response = "Please type something.\r\n";
      }
      else if ("bye".equals(request.toLowerCase())) {
          response = "Have a good day!\r\n";
          close = true;
      }
      else {
          response = "Did you say '" + request + "'?\r\n";
      }
      
      Map<String, Object> requestMap = gson.fromJson(request, Map.class);
      
      String userID = null;
      
      
      // 로그인
      // 해당 유저가 있는지 검사(메모리DB검사)
      UserState userState = cacheUser.get(userID);
      
      // 없으면, 에러 리턴
      if(userState ==  null) {
      	return;
      }
      
      // 있으면, 비번 검사
      
      Object obj = new Object();
      
      WebSocketFrame wf = (WebSocketFrame)obj;
      

      // We do not need to write a ChannelBuffer here.
      // We know the encoder inserted at TelnetPipelineFactory will do the conversion.
      ChannelFuture future = ctx.write(response);

      // Close the connection after sending 'Have a good day!'
      // if the client has sent 'bye'.
      if (close) {
          future.addListener(ChannelFutureListener.CLOSE);
      }
	}
	
	@Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
      ctx.flush();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	  logger.error("## exceptionCaught...", cause);
      ctx.close();
  }

}
