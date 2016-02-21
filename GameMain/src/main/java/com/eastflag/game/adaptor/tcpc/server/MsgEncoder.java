package com.eastflag.game.adaptor.tcpc.server;

import org.slf4j.*;

import com.eastflag.game.adaptor.tcpc.message.Message;
import com.eastflag.game.adaptor.tcpc.util.HexLog;

import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.handler.codec.*;

/**
 * @author Shaky
 *
 */
public class MsgEncoder extends MessageToByteEncoder<Message> {
	private final Logger log = LoggerFactory.getLogger(MsgEncoder.class);
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
		try {
			// message-type (4)
			out.writeInt(msg.getHeader().getMessageType());
			
			// message-length (4)
			int messageLength = msg.getHeader().getMessageLength(); 
			out.writeInt(messageLength);
			
//			// MessageID(4)
//			out.writeInt(msg.getMessage_id());
//			
//			
//			// TransactionID(12)
//			if(msg.getTransaction_id().equals("0"))
//			{
//				ByteBuf TransactionID = ctx.alloc().buffer(12);
//				TransactionID.writeBytes(msg.getTransaction_id().getBytes());
//				int TransactionIDsize = TransactionID.writableBytes();
//				for(int i = 0; i < TransactionIDsize; i++){TransactionID.writeByte(0x00);}
//				out.writeBytes(TransactionID);
//				TransactionID.release();
//			}
//			else
//			{
//				ByteBuf TransactionID = ctx.alloc().buffer(12);
//				TransactionID.writeBytes(msg.getTransaction_id().getBytes());
//				out.writeBytes(TransactionID);
//				TransactionID.release();
//			}
//			
//			
//			// ChannelID(14)
//			ByteBuf ChannelID = ctx.alloc().buffer(14);
//			ChannelID.writeBytes(msg.getChannel_id().getBytes());
//			int ChannelIDsize = ChannelID.writableBytes();
//			for(int i = 0; i < ChannelIDsize; i++){ChannelID.writeByte(0x00);}
//			out.writeBytes(ChannelID);
//			ChannelID.release();
//			
//			
//			// Reserved1(2)
//			ByteBuf Reserved1 = ctx.alloc().buffer(2);
//			Reserved1.writeBytes(msg.getReserved1().getBytes());
//			int Reserved1size = Reserved1.writableBytes();
//			for(int i = 0; i < Reserved1size; i++){Reserved1.writeByte(0x00);}
//			out.writeBytes(Reserved1);
//			Reserved1.release();
//			
//			
//			// DestinationIP(16)
//			ByteBuf DestinationIP = ctx.alloc().buffer(16);
//			DestinationIP.writeBytes(msg.getDestination_ip().getBytes());
//			int DestinationIPsize = DestinationIP.writableBytes();
//			for(int i = 0; i < DestinationIPsize; i++){DestinationIP.writeByte(0x00);}
//			out.writeBytes(DestinationIP);
//			DestinationIP.release();
//			
//			
//			// Reserved2(12)
//			ByteBuf Reserved2 = ctx.alloc().buffer(12);
//			Reserved2.writeBytes(msg.getReserved2().getBytes());
//			int Reserved2size = Reserved2.writableBytes();
//			for(int i = 0; i < Reserved2size; i++){Reserved2.writeByte(0x00);}
//			out.writeBytes(Reserved2);
//			Reserved2.release();
//			
//			
//			// DATA Length(4)
//			out.writeInt(msg.getData_length());
			
			
//			if(msg.getData_length() > 0)
//			{
//				// DATA
//				ByteBuf DATA = ctx.alloc().buffer(msg.getData_length());
//				DATA.writeBytes(msg.getData_section().getBytes(SmsProperties.getInst().getEncoding_type()));
//				out.writeBytes(DATA);
//				DATA.release();
//			}
			
			if(messageLength > 0) {
				// DATA
				ByteBuf data = ctx.alloc().buffer(messageLength);
				data.writeBytes(msg.getBody().encodeAllFields());
				out.writeBytes(data);
				data.release();
			}
			
			log.debug("{}", HexLog.formatByteBuf("[SEND]", out));
		}
		catch(Exception e) {
			log.error("[SmsEncoder.encode()]", e);
		}
	}
}
