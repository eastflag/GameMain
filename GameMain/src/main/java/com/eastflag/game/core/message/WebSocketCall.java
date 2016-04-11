package com.eastflag.game.core.message;

public class WebSocketCall implements Call {
	
	private String reqMessage;
	private String serviceId;

	public WebSocketCall() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	@Override
	public String getServiceId() {
		return serviceId;
	}
	
	@Override
	public String getReqMessage() {
		return reqMessage;
	}

	@Override
	public void setReqMessage(String reqMessage) {
		this.reqMessage = reqMessage;
	}
}
