package com.eastflag.game.core.service;

import com.eastflag.game.core.message.Call;

public interface Service {
	public String getServiceId();
	public boolean excuteCall(Call call);
}
