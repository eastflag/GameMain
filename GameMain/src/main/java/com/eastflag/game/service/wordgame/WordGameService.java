package com.eastflag.game.service.wordgame;

import java.util.Map;

import com.eastflag.game.core.dao.CommonDao;
import com.eastflag.game.core.dao.DataMap;
import com.eastflag.game.core.message.Call;
import com.eastflag.game.core.service.Service;
import com.eastflag.game.service.wordgame.dao.UserDao;
import com.eastflag.game.service.wordgame.vo.UserVo;
import com.google.gson.Gson;

public class WordGameService implements Service {

	private String serviceId;
	private Gson gson;
	
	//private CommonDao commonDao;
	//private CommonRedis commonRedis;
	
	private UserDao userDao;
	
	public WordGameService() {
		super();
		
		gson = new Gson();
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public WordGameService(String serviceId) {
		super();
		this.serviceId = serviceId;
	}
	
	public boolean excuteCall(Call call) {
		
		String reqMessage = call.getReqMessage();
		
		Map<String, Object> reqMap = gson.fromJson(reqMessage, Map.class);
		
		
		
		reqMap.get("command_type");
		
		
		
		
		
		
		// DB 테스트
		UserVo user = userDao.getUser("collme74");
		
		
		
		return false;
	}
	
	public String getServiceId() {
		// TODO Auto-generated method stub
		return null;
	}

}
