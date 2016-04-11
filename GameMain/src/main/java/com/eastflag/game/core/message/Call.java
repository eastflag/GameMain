package com.eastflag.game.core.message;

public interface Call {
	public void setServiceId(String serviceId);
	public String getServiceId();
	public String getReqMessage();
	public void setReqMessage(String reqMessage);

}
