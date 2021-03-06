package com.eastflag;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.eastflag.client.WebSocketClient;
import com.eastflag.game.GameMainApplication;
import com.google.gson.Gson;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = GameMainApplication.class)
//@WebAppConfiguration
public class GameMainApplicationTests {
	
	public WebSocketClient client; 
	
	@Before
	public void init() {
		client = new WebSocketClient();
		try {
			client.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void contextLoads() {
	}
	
	@Test
	public void auth_Login() {
		// Make protocol
		
		Map<String, Object> request = new HashMap<String, Object>();
		Map<String, String> reqData = new HashMap<String, String>();
		request.put("req_data", reqData);
		
		request.put("cmd_type", "user_login_info");
		request.put("msg_type", "request");

		reqData.put("user_id", "collme74");
		reqData.put("user_pwd", "collme74");
		reqData.put("token", "abcdefg");
		
		Gson gson = new Gson();
		
		String json = gson.toJson(request);
		
		// Sends data line to the server.
		Channel ch = client.getChannel();
		ch.writeAndFlush(new TextWebSocketFrame(json));
		
		ChannelFuture closeFuture;
		try {
			closeFuture = ch.closeFuture().sync();
			
			if(closeFuture.isSuccess()) {
				
			}
			else {
				
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

		fail("Not yet implemented");
	}

}
