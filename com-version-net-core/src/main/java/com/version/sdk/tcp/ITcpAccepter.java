package com.version.sdk.tcp;

public interface ITcpAccepter {
	
	void afterAccepter();
	void beforeAccepter();
	void start(String netIp,String netPort) throws Exception;
}
