package com.eastflag.game.core.adaptor;

import com.eastflag.game.core.message.Call;
import com.eastflag.game.core.worker.TaskDistributor;

public abstract class AbstractAdaptor {
	private TaskDistributor worker;
	
	public abstract void activate();
	
	public void dispatch(Call call) {
		worker.put(call);
	}
}
