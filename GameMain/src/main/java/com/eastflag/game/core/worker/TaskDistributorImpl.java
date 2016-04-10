package com.eastflag.game.core.worker;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Component;

import com.eastflag.game.core.message.Call;
import com.eastflag.game.core.service.ServiceManager;

@Component
public class TaskDistributorImpl implements TaskDistributor {
//	private BlockingQueue<Call> queue;
	
	private ServiceManager serviceManager;
	private ExecutorService executorService;
	
	public TaskDistributorImpl() {	
		//queue = new ArrayBlockingQueue<Call>(1000, true);
		executorService = Executors.newFixedThreadPool(200);
	}
	
	public void init() {
		// TODO
		// queue 에서 꺼내 작업을 시작한다.(쓰레드)
	}
	
	@Override
	public void put(Call call) throws Exception {
		CallTask callTask = new CallTask(serviceManager, call);
		Future<Boolean> taskResult = executorService.submit(callTask);
		
		try {
			taskResult.get(10, TimeUnit.SECONDS);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		catch (ExecutionException e) {
			e.printStackTrace();
		}
		catch (TimeoutException e) {
			e.printStackTrace();
		}
		
//		String serviceId = call.getServiceId();
//		Service service = serviceManager.getService(serviceId);
//		
//		service.excuteCall(call);
	}
	
	public ServiceManager getServiceManager() {
		return serviceManager;
	}

	public void setServiceManager(ServiceManager serviceManager) {
		this.serviceManager = serviceManager;
	}
	
	
//	public void run() {
//		
//		while(true) {
//			try {
//				Call call = queue.take();
//				
//				
//				String serviceId = call.getServiceId();
//				
//				
//				Service service = serviceManager.getService(serviceId);
//				
//				// TODO
//				// 이렇게 하면 시간이 소요되기 때문에, ThreadPool 로 돌릴 필요가 있다.
//				service.excuteCall(call);
//				
//			}
//			catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}

}
