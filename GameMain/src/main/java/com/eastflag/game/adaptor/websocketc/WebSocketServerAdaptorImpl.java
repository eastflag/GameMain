package com.eastflag.game.adaptor.websocketc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eastflag.game.adaptor.websocketc.server.WebSocketServer;
import com.eastflag.game.core.adaptor.AbstractAdaptor;
import com.eastflag.game.core.message.Call;
import com.eastflag.game.core.worker.TaskDistributor;
import com.eastflag.game.service.wordgame.dao.UserDao;
import com.eastflag.game.service.wordgame.vo.UserVo;

public class WebSocketServerAdaptorImpl extends AbstractAdaptor implements WebSocketServerAdaptor {
	private final Logger logger = LoggerFactory.getLogger(WebSocketServerAdaptorImpl.class.getName());
	
	private TaskDistributor taskDistributor;
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public WebSocketServerAdaptorImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public TaskDistributor getTaskDistributor() {
		return taskDistributor;
	}

	public void setTaskDistributor(TaskDistributor taskDistributor) {
		this.taskDistributor = taskDistributor;
	}

	@Override
	public void activate() {
		WebSocketServer server = new WebSocketServer(this);
		server.start();
	}
	
	@Override
	public void dispatchCall(Call call) throws Exception {
//		try {
//			UserVo user = userDao.getUser("collme74");
//			logger.debug("## UserVo : " + user);
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
		
		
		dispatch(call);
	}

	@Override
	public void respondMessage(Call call) {
		
	}
	
}
