package com.eastflag.game.adaptor.tcpc.message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class MessageHeader {
	private int messageType;
	private int messageLength;
	
	public MessageHeader() {
		//생성 시 타입과 길이를 0으로 초기화
		messageType = 0;
		messageLength = 0;
	}
	
	public MessageHeader(int messageType, int messageLength) {
		//생성 시 타입과 길이를 0으로 초기화
		this.messageType = messageType;
		this.messageLength = messageLength;
	}
	
	public byte[] encodeAllFields() throws IOException {
		byte[] encodedResultByteArray = null;
		
		ByteArrayOutputStream baos = null;
		DataOutputStream dos = null;
		
		try {
			baos = new ByteArrayOutputStream();
			
			dos = new DataOutputStream(baos);
			dos.writeInt(messageType);
			dos.writeInt(messageLength);
			
			encodedResultByteArray = baos.toByteArray();
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
		return encodedResultByteArray;
	}
	
	public void decodeAllFields(byte[] buffer) throws IOException {
		DataInputStream dis = null;
		try {
			dis = new DataInputStream(new ByteArrayInputStream(buffer));
			messageType = dis.readInt();
			messageLength = dis.readInt();
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
	
	
	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public int getMessageLength() {
		return messageLength;
	}

	public void setMessageLength(int messageLength) {
		this.messageLength = messageLength;
	}

}
