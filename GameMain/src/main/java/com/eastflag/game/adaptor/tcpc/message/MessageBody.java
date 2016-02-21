package com.eastflag.game.adaptor.tcpc.message;

import java.io.IOException;

public interface MessageBody {
	public byte[] encodeAllFields() throws IOException;
	public void decodeAllFields(byte[] buffer) throws IOException;
	public int getMessageType();
}
