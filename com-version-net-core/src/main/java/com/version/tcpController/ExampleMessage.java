package com.version.tcpController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.alibaba.fastjson.JSONObject;
import com.version.common.annotation.IProcess;
import com.version.common.util.LoggerUtil;
import com.version.common.util.SpringContextUtils;
import com.version.common.work.Work;
import com.version.doService.ExampleServiceImpl;
import com.version.sdk.server.netty.Message;

public class ExampleMessage implements Work{

	
	private static final long serialVersionUID = 252564223713197295L;

	private Message message;  
	private Method method ;
	@Override
	public void run() {
		try {
			System.err.println("没执行"+method.getName());
			
			method.invoke(SpringContextUtils.getBean(method.getDeclaringClass()),null,null);
		} catch (Exception e) {
			System.err.println("执行方法异常");
			e.printStackTrace();
		} 
	}

	@Override
	public void init(Object... objs) throws Exception {
		//Message message = (Message)objs[0];
		this.method =(Method)objs[0];
		//this.t = JSONObject.parseObject(message.getData(), String.class);
		
	}

}
