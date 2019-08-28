package com.version.sdk.tcp;

public class TcpFactory {
	/**
	 * 获取netty TCPIP 客服端
	 * @return
	 */
	public static TcpClient getTcpClient() {
		return TcpClient.getTcpClient;
	}
	public static TcpSever getTcpSever() {
		return TcpSever.getTcpSever;
	}
	
	public  static TcpAccepter getTcpAccepter(boolean isTcpSever) {
		if(isTcpSever) {
			return getTcpSever();
		}else {
			return getTcpClient();
		}
	}
}
