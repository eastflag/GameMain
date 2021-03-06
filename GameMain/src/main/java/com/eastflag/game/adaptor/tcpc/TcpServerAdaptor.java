package com.eastflag.game.adaptor.tcpc;

import com.eastflag.game.core.message.Call;

public interface TcpServerAdaptor {
	public void dispatchMessage(Call call) throws Exception;
	public void respondMessage(Call call);
}
