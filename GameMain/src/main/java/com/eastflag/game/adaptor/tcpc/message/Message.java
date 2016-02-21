package com.eastflag.game.adaptor.tcpc.message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Message {
	private static final Logger logger = LoggerFactory.getLogger(Message.class);
	
	protected MessageHeader header;
	protected MessageBody body;

	public Message() {
	
	}
	
	public Message(MessageBody body) {
		int messageType = 0;
		int messageLength = 0;
		try {
			messageType = body.getMessageType();
			messageLength = body.encodeAllFields().length;
			
			MessageHeader header = new MessageHeader(messageType, messageLength);
			
			this.header = header;
			this.body = body;
		} catch (IOException e) {
			logger.error("MessageBody encode fail. MessageType: " + messageType);
		}
	}
	
	public Message(MessageHeader header, MessageBody body) {
		this.header = header;
		this.body = body;
	}
	
	public byte[] encodeHeader() throws IOException {
		byte[] byteArray = null;
		
		ByteArrayOutputStream baos = null;
		DataOutputStream dos = null;
		
		try {
			baos = new ByteArrayOutputStream();
			dos = new DataOutputStream(baos);
	
			dos.writeInt(header.getMessageType());
			dos.writeInt(header.getMessageLength());
			
			byteArray = baos.toByteArray();
		} catch (IOException e) {
			throw e;
		} finally {
			if(dos != null){
				try {
					dos.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
		return byteArray;
	}
	
	public void decodeHeader(byte[] headerBuffer) throws IOException {
		int messageType = 0;
		int messageLength = 0;
		
		DataInputStream dis = null;
		
		try {
			dis = new DataInputStream(new ByteArrayInputStream(headerBuffer));
			
			messageType = dis.readInt();
			messageLength = dis.readInt();
			
			header.setMessageType(messageType);
			header.setMessageLength(messageLength);
		} catch (IOException e) {
			throw e;
		} finally {
			if(dis != null){
				try {
					dis.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
	}
	
	public MessageHeader getHeader() {
		return header;
	}
	
	public void setHeader(MessageHeader header) {
		this.header = header;
	}
	
	public void setHeader(int messageType, int messageLength) {
		MessageHeader header = new MessageHeader(messageType, messageLength);
		this.header = header;
	}
	
	public MessageBody getBody() {
		return body;
	}
	
	public void setBody(MessageBody body) {
		this.body = body;
	}
	
	// TODO 추후 구현
	public int getMessage_id() {
		return 0;
	}
	
	// TODO 추후 구현
	public String getTransaction_id() {
		return null;
	}
	
	public abstract void decodeBody(byte[] buffer) throws IOException;
	public abstract byte[] encodeBody() throws IOException;

	
}
