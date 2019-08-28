package com.version.sdk.common;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.websocket.Session;

import com.alibaba.fastjson.JSON;
import com.version.common.util.LoggerUtil;
import com.version.sdk.server.netty.Message;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

public class IoSender {

	public static void sendWebsocketMsg(Session session, int code, byte[] bytes) throws IOException {
		if (session.isOpen()) {
			ByteBuffer byteBuffer = ByteBuffer.allocate(8 + bytes.length);
			byteBuffer.putInt(code);
			byteBuffer.putInt(8 + bytes.length);
			byteBuffer.put(bytes);
			byteBuffer.flip();
			session.getBasicRemote().sendBinary(byteBuffer);
			session.getBasicRemote().flushBatch();
		}
	}
	/**
	 * tcp发送消息
	 * @param session
	 * @param code
	 * @param obj
	 */
	public static void sendTcpMsg(Channel  session, int code, Object obj) {
		try {
			Message message = new Message();

			if(session.isOpen()) {
				
				byte[] data =  JSON.toJSONString(obj).getBytes();
				message.setCode(code);
				message.setData(data);
				message.setLength(data.length +8);
				ChannelFuture future =  session.writeAndFlush(message);
				
				if(future.isSuccess()) {
					LoggerUtil.info("消息发送成功");
				}
			
			}
		} catch (Exception e) {
			System.err.println("io异常sendTcpMsg");
		}
		
		
	}
}
