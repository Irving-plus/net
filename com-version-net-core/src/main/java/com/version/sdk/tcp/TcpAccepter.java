package com.version.sdk.tcp;

public abstract class TcpAccepter implements ITcpAccepter {
	private String netIp;
	private String netPort;

	public TcpAccepter() {
		
	}

	public TcpAccepter  initServer(String netIp, String netPort) {
		this.netIp = netIp;
		this.netPort = netPort;
		return this;
	}
	public String getNetIp() {
		return netIp;
	}
	public void setNetIp(String netIp) {
		this.netIp = netIp;
	}
	public String getNetPort() {
		return netPort;
	}
	public void setNetPort(String netPort) {
		this.netPort = netPort;
	}

	
}
