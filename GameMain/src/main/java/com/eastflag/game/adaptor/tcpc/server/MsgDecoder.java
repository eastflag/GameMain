package com.eastflag.game.adaptor.tcpc.server;

import java.util.*;

import org.slf4j.*;

import com.eastflag.game.adaptor.tcpc.message.RawMessage;
import com.eastflag.game.adaptor.tcpc.util.HexLog;

import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.handler.codec.*;

/**
 * @author Shaky
 *
 */
public class MsgDecoder extends ByteToMessageDecoder {

	private final Logger log = LoggerFactory.getLogger(MsgDecoder.class);
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		
		try {
			// HEADER가 64바이트
			if (in.readableBytes() < 8) { return; }
			in.markReaderIndex();
			
			log.debug("{}", HexLog.formatByteBuf("[RECV]", in));
			
			//ResMessage msg = new ResMessage();
			RawMessage rawMsg = new RawMessage();
			
			// message-type (4)
			rawMsg.setMessageType(in.readInt());
			
			// message-length (4)
			int messageLength = in.readInt();
			rawMsg.setMessageLength(messageLength);
			
//			// MessageID(4)
//			msg.setMessage_id(in.readInt());
//			
//			// TransactionID(12)  
//			byte[] transactionID = new byte[12];
//			in.readBytes(transactionID);
//			String trans_id = new String(transactionID);
//			
//			if(trans_id.replaceAll("\u0000", "").equals("0"))
//			{
//				msg.setTransaction_id("0");
//			}
//			else
//			{
//				msg.setTransaction_id(trans_id);
//			}
//			
//			
//			// DestinationIP(16)
//			byte[] destinationIP = new byte[16];
//			in.readBytes(destinationIP);
//			msg.setDestination_ip(new String(destinationIP).replaceAll("\u0000", ""));
//			
//			
//			// ChannelID(14)
//			byte[] channelID = new byte[14];
//			in.readBytes(channelID);
//			msg.setChannel_id(new String(channelID).replaceAll("\u0000", ""));
//			
//			
//			// Reserved1(2)
//			byte[] reserved1 = new byte[2];
//			in.readBytes(reserved1);
//			msg.setReserved1(new String(reserved1));
//			
//			
//			// Reserved2(12)
//			byte[] reserved2 = new byte[12];
//			in.readBytes(reserved2);
//			msg.setReserved2(new String(reserved2));
//			
//			
//			// DATA Length(4)
//			int data_len = in.readInt();
//			msg.setData_length(data_len);
			
			
//			if(data_len > 0) {
//				// DATA 만큼 수신?榮쩝? 확인
//				if (in.readableBytes() < data_len) 
//				{
//					in.resetReaderIndex();
//					return; 
//				}
//				
//				if(data_len == 2)
//				{
//					// 상태메시지 (PINGPONG)
//					short data = in.readShort();
//					msg.setData_section(String.valueOf(data));
//				}
//				else if(data_len == 4)
//				{
//					// Result(2)
//					byte[] result = new byte[2];
//					in.readBytes(result);
//					msg.setResult(new String(result, SmsProperties.getInst().getEncoding_type()));
//					
//					// Reason(2)
//					short reason = in.readShort();
//					msg.setData_section(String.valueOf(reason));
//				}
//				else
//				{
//					// RESULT
//					byte[] result = new byte[2];
//					in.readBytes(result);
//					msg.setResult(new String(result, SmsProperties.getInst().getEncoding_type()));
//					
//					// DATA
//					byte[] data = new byte[msg.getData_length() - 2];
//					in.readBytes(data);
//					msg.setData_section(new String(data, SmsProperties.getInst().getEncoding_type()));
//				}
//			}
			
			if(messageLength > 0) {
				// DATA 만큼 수신 확인
				if (in.readableBytes() < messageLength) {
					in.resetReaderIndex();
					return; 
				}
				
				// DATA
				byte[] data = new byte[messageLength];
				in.readBytes(data);
				rawMsg.setBody(data);
			}
			
			out.add(rawMsg);
		}
		catch(Exception e) {
			log.error("[SmsDecoder.decode()]", e);
		}
	}

}
