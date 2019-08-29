package com.version.tcpController;

import org.springframework.beans.factory.annotation.Autowired;

import com.version.common.annotation.IProcess;
import com.version.common.annotation.TCPController;
import com.version.common.entity.client.SuperClient;
import com.version.doService.ExampleServiceImpl;

@TCPController(name = "demo")
public class DemoController {
	@Autowired
	private ExampleServiceImpl serviceImpl;
	
	@IProcess(code =201)
	public void begin(String id,SuperClient superClient) {
		System.err.println("调用的方法:"+this);
		serviceImpl.doSomeThing("cbx");
	}
	
	@IProcess(code =202)
	public void join(String id,SuperClient superClient) {
			
	}
}
