package com.eastflag;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.eastflag.client.WebSocketClient;
import com.eastflag.game.GameMainApplication;
import com.eastflag.game.service.wordgame.vo.LoginRequestVO;
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
		
		LoginRequestVO loginReqVO = new LoginRequestVO();
		loginReqVO.setId("collme74");
		loginReqVO.setPassword("callme74");
		
		Gson gson = new Gson();
		
		String json = gson.toJson(loginReqVO);
		
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
