package com.version.sdk.tcp;

import com.version.common.manager.ProcessManager;
import com.version.common.util.LoggerUtil;

public class TcpSever extends TcpAccepter{

	public static TcpSever	 getTcpSever  = new TcpSever();
	
	private TcpSever() {
	
	}

	@Override
	public void afterAccepter() {
		LoggerUtil.info("========================================服务器启动成功");
		
	}

	@Override
	public void beforeAccepter() {
		LoggerUtil.info("========================================开始启动服务器");
		
	}

	@Override
	public void start(String netIp,String netPort) throws Exception {
		beforeAccepter();
		try {
			//启动服务器
			initServer(netIp, netPort);
			ProcessManager.getManager().start();
			//开启netty服务器启动监听
			TcpManager.getManager().createSeverTcp(super.getNetIp(), super.getNetPort());
			afterAccepter();
		} catch (Exception e) {
			System.exit(0);
		}
		
	}

}
