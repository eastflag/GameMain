package com.eastflag.game.core.message;

public class WebSocketCall implements Call {
	
	private String reqMessage;

	public WebSocketCall() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getServiceId() {
		// TODO Auto-generated method stub
		return null;
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
