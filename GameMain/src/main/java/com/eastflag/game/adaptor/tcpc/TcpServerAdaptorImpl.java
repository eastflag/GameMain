package com.eastflag.game.adaptor.tcpc;

import javax.annotation.Resource;

import com.eastflag.game.adaptor.tcpc.server.TcpServer;
import com.eastflag.game.core.adaptor.AbstractAdaptor;
import com.eastflag.game.core.message.Call;
import com.eastflag.game.core.worker.TaskDistributor;

public class TcpServerAdaptorImpl extends AbstractAdaptor implements TcpServerAdaptor {
	
	private TaskDistributor taskDistributor;
	
	public TcpServerAdaptorImpl() {
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
		TcpServer server = new TcpServer();
		server.start();
	}

	public void respondMessage(Call call) {
		// TODO Auto-generated method stub
		
	}

	public void dispatchMessage(Call call) {
		dispatch(call);
	}
	
//	public static void main(String[] args) {
//		TcpServerAdaptorImpl adaptor = new TcpServerAdaptorImpl();
//		adaptor.activate();
//	}

}
