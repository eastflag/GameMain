package com.eastflag.game.service.wordgame;

import java.util.Map;

import com.eastflag.game.adaptor.websocketc.WebSocketServerAdaptor;
import com.eastflag.game.core.dao.CommonDao;
import com.eastflag.game.core.dao.DataMap;
import com.eastflag.game.core.message.Call;
import com.eastflag.game.core.service.Service;
import com.eastflag.game.core.service.ServiceManager;
import com.eastflag.game.service.wordgame.dao.UserDao;
import com.eastflag.game.service.wordgame.vo.UserVo;
import com.google.gson.Gson;

public class WordGameService implements Service {

	private String serviceId;

	private Gson gson;
	
	//private CommonDao commonDao;
	//private CommonRedis commonRedis;
	
	private UserDao userDao;
	private WebSocketServerAdaptor wssAdaptor;
	private ServiceManager serviceManager;
	
	public WordGameService() {
		super();
		
		gson = new Gson();
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void excuteCall(Call call) {
		
		String reqMessage = call.getReqMessage();
		
		Map<String, Object> reqMap = gson.fromJson(reqMessage, Map.class);
		
		
		String cmdType = (String)reqMap.get("cmd_type");
		
		if(cmdType.equals("user_login_info")) {
			// DB 테스트
			UserVo user = userDao.getUser("collme74");
			
			// 응답
			wssAdaptor.respondMessage(call);
		}
		else {
			
		}
	}
	
	public String getServiceId() {
		return serviceId;
	}
	
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public ServiceManager getServiceManager() {
		return serviceManager;
	}

	public void setServiceManager(ServiceManager serviceManager) {
		serviceManager.addService(this);
		this.serviceManager = serviceManager;
	}
	
	public void setWebSocketServerAdaptor(WebSocketServerAdaptor wssAdaptor) {
		this.wssAdaptor = wssAdaptor;
	}

}
