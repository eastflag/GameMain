package com.eastflag.game.adaptor.websocketc;

import com.eastflag.game.core.message.Call;

public interface WebSocketServerAdaptor {
	public void dispatchCall(Call call);
	public void respondMessage(Call call);
}
