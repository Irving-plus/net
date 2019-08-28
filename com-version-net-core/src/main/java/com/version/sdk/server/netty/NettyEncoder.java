package com.version.sdk.server.netty;

import com.version.common.util.LoggerUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
/**
 * <pre>
 * 数据包格式 
 * |  命令号 	|  总长度  |  数据     |
 * </pre>
 */
public class NettyEncoder extends MessageToByteEncoder<Message>{
	public NettyEncoder(){
		
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf buffer) throws Exception {
		buffer.writeInt(msg.getCode());
		buffer.writeInt(msg.getLength());
		buffer.writeBytes(msg.getData());
	}
}
