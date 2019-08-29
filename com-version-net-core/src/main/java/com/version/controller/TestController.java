package com.version.controller;

import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.version.common.entity.client.SuperClient;
import com.version.common.entity.client.TcpSocketClient;
import com.version.common.manager.ServerSessionManager;
import com.version.sdk.common.IoSender;
import com.version.sdk.tcp.TcpManager;

@RestController
public class TestController {
	
	@GetMapping("/severMess")
	public Object severMess(int code,String msg) {
		ServerSessionManager sessionManager = ServerSessionManager.getManager();
		for(Entry<String,SuperClient> entroy: sessionManager.clients.entrySet() ) {
			SuperClient client = entroy.getValue();
			if( client instanceof TcpSocketClient) {
				TcpSocketClient tcpSocketClient = (TcpSocketClient)client;
				IoSender.sendTcpMsg(tcpSocketClient.getSession(), code, msg);
				
			}
			
			//System.err.println(entroy.getKey());
		}
		
		return  JSONObject.toJSONString( sessionManager.clients.size()) ;
	}
	/*
	@GetMapping("/clientMess")
	public Object clientMess(int code,String mes) {
		ServerSessionManager sessionManager = ServerSessionManager.getManager();
		for(Entry<String,SuperClient> entroy: sessionManager.clients.entrySet() ) {
			SuperClient client = entroy.getValue();
			if( client instanceof TcpSocketClient) {
				TcpSocketClient tcpSocketClient = (TcpSocketClient)client;
				IoSender.sendTcpMsg(tcpSocketClient.getSession(), 200, "bingxi有点乖");
				
			}
			
			//System.err.println(entroy.getKey());
		}
		
		return  JSONObject.toJSONString( sessionManager.clients) ;
	}*/
	

	@GetMapping("/doTask")
	public Object doTask() {
		
		ExecutorService threadPool2 =  Executors.newFixedThreadPool(50);
        CompletionService<String> completionService = new ExecutorCompletionService<String>(threadPool2);
        
        for(int i=1;i<=10;i++){
            final int seq = i;
            completionService.submit(()->{
            		
            	    int time = new Random().nextInt(5000);
                    Thread.sleep(time);
                    return  seq+"_"+time;
                
            });
        }
        long t1 = System.currentTimeMillis();
        for(int i=0;i<10;i++){
            try {
                System.out.println(
                        completionService.take().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        long t2 = System.currentTimeMillis();
		System.out.println("do time:" +(t2 - t1));
		return t2 -t1;
	}
	@GetMapping("/addClient")
	public void addClient() {
		try {
			TcpManager.getManager().createClientTcp("127.0.0.1","99");
			severMess(201, "cbx");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
