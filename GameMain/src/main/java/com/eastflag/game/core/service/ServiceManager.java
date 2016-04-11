package com.eastflag.game.core.service;

public interface ServiceManager {
	public Service getService(String serviceId);
	public void addService(Service service);
}
