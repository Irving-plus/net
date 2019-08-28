package com.version.doService;

import org.springframework.stereotype.Service;

import com.version.common.util.LoggerUtil;

@Service
public class ExampleServiceImpl {
	public void doSomeThing(String str){
	
		LoggerUtil.info("执行service"+ str);
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
