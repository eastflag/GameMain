package com.eastflag.game.adaptor.websocketc;

import org.eclipse.jetty.websocket.server.WebSocketServerConnection;

import com.eastflag.game.core.adaptor.AbstractAdaptor;
import com.eastflag.game.core.message.Call;
import com.eastflag.game.core.worker.TaskDistributor;

public class WebSocketServerAdaptorImpl extends AbstractAdaptor implements WebSocketServerAdaptor {
	
	private TaskDistributor taskDistributor;

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
		WebSocketServer server = new WebSocketServer();
		server.start();
	}
	
	@Override
	public void dispatchMessage(Call call) {
		// TODO Auto-generated method stub
	}

	@Override
	public void respondMessage(Call call) {
		// TODO Auto-generated method stub
	}
	
}
