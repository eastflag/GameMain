package com.eastflag.game.core.service;

import java.util.HashMap;
import java.util.Map;

public class ServiceManagerImpl implements ServiceManager {
	private Map<String, Service> mapService;

	public ServiceManagerImpl() {
		mapService = new HashMap<String, Service>();
	}

	public Service getService(String serviceId) {
		return mapService.get(serviceId);
	}

	@Override
	public void addService(Service service) {
		mapService.put(service.getServiceId(), service);
	}

}
