package com.eastflag.game.core.worker;

import com.eastflag.game.core.message.Call;

public interface TaskDistributor {
	public void put(Call call);
}
