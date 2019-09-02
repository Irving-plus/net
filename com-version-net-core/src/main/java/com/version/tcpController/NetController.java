package com.version.tcpController;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.version.common.annotation.IProcess;
import com.version.common.annotation.TCPController;
import com.version.common.manager.ThreadLocalManager;
import com.version.doService.ExampleServiceImpl;

@TCPController(name = "net")
public class NetController extends BaseTcpController{
	@Autowired
	private ExampleServiceImpl serviceImpl;
	
	@IProcess(code =201)
	public void joinGame(String json) {
		System.err.println("请求的数据201:"+json);
		serviceImpl.doSomeThing("cbx");
		System.err.println(JSONObject.toJSONString(ThreadLocalManager.getThreadLocalManager().getThreadLocal()));
		long endTime = System.currentTimeMillis();
		System.err.println(Thread.currentThread().getName());
		System.err.println("执行时间"+(endTime -getBeginTime())+"毫秒");
	}
	
	@IProcess(code =202)
	public void join(String json) {
		System.err.println("请求的数据:201"+json);
		long endTime = System.currentTimeMillis();
		System.err.println("执行时间"+(endTime -getBeginTime())+"毫秒");
	}
}
