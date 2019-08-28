package com.version.doMessage;

import com.alibaba.fastjson.JSONObject;
import com.version.common.annotation.IProcess;
import com.version.common.util.LoggerUtil;
import com.version.common.util.SpringContextUtils;
import com.version.common.work.Work;
import com.version.doService.ExampleServiceImpl;
import com.version.sdk.server.netty.Message;
@IProcess(code=200)
public class ExampleMessage implements Work{

	
	private static final long serialVersionUID = 252564223713197295L;
	private Message message;  
 
	@Override
	public void run() {
		LoggerUtil.info("这是一个run方法{}", message.getCode());
		//System.err.println("这是一个run方法"+ message.getCode());
		ExampleServiceImpl serviceImpl=  (ExampleServiceImpl)SpringContextUtils.getBean(ExampleServiceImpl.class);
		serviceImpl.doSomeThing(JSONObject.parseObject(message.getData(), String.class));
	}

	@Override
	public void init(Object... objs) throws Exception {
		Message message = (Message)objs[0];
		this.message = message;
		//this.t = JSONObject.parseObject(message.getData(), String.class);
		
	}

}
