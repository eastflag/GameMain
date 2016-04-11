package com.eastflag.game.core.worker;

import java.util.concurrent.Callable;

import com.eastflag.game.core.message.Call;
import com.eastflag.game.core.service.Service;
import com.eastflag.game.core.service.ServiceManager;

public class CallTask implements Callable<Boolean> {
	private ServiceManager serviceManager;
	private Call call;

	public CallTask() {
		// TODO Auto-generated constructor stub
	}

	public CallTask(ServiceManager serviceManager, Call call) {
		super();
		this.serviceManager = serviceManager;
		this.call = call;
	}

	public Boolean call() throws Exception {
		//
		String serviceId = call.getServiceId();
		Service service = serviceManager.getService(serviceId);
		
		service.excuteCall(call);
		
		return Boolean.valueOf(true);
	}

}
