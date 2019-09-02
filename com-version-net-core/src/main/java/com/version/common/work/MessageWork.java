package com.version.common.work;

import java.lang.reflect.Method;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.version.common.entity.Controller;
import com.version.common.entity.ThreadLocalObject;
import com.version.common.entity.client.SuperClient;
import com.version.common.manager.CacheServer;
import com.version.common.manager.RoomManager;
import com.version.common.manager.ThreadLocalManager;
import com.version.common.util.LoggerUtil;
import com.version.common.util.SpringContextUtils;
import com.version.game.LogicController;
import com.version.game.Room;
import com.version.sdk.netty.Message;

public class MessageWork implements Work{

	
	private static final long serialVersionUID = 252564223713197295L;

	private Object data;  
	private Method method ;
	private SuperClient superClient;
	private long beginTime;
	private ThreadLocalObject threadLocalObject;
	/**
	 * 业务线程调用
	 */
	@Override
	public void run() {
		try {		
		
			ThreadLocalManager.getThreadLocalManager().setThreadLocal(threadLocalObject);
			Thread.currentThread().setName(Thread.currentThread().getName()+":"+method.getName());
			method.invoke(SpringContextUtils.getBean(method.getDeclaringClass()),data);

		} catch (Exception e) {
			LoggerUtil.error(e.getMessage());
			e.printStackTrace();
		} 
	}
	/**
	 * netty线程组调用
	 */
	@Override
	public void init(Object... objs) throws Exception {

		 this.method = (Method)objs[0];
		 Message msg  = (Message)objs[1];
		 this.superClient = (SuperClient)objs[2];
		 this.beginTime = (Long)objs[3];
		 this.data  = JSONObject.parseObject(msg.getData(),  method.getGenericParameterTypes()[0]);
		 int code = msg.getCode();
		 ThreadLocalObject localObject = new ThreadLocalObject();
		 //200加入逻辑服 
		 //初始化用户,房间信息
		 if(code !=200) {
			 Controller controller = CacheServer.getCache().getOnlineaccounts().get(superClient.getSessionId());
			 if(controller instanceof LogicController) {
				 LogicController logicController = (LogicController) controller;	
				 String roomId= logicController.getRoomId();
				 if(!StringUtils.isEmpty(roomId)) {
					 Room room = RoomManager.getRooms().getRoomById(logicController.getRoomId());	
					 localObject.setRoom(room);
				 }
				 localObject.setController(logicController);
				 
				
			}else {
				localObject.setController(controller);
			}
				 
		 }
		 localObject.setBeginTime(beginTime);		
		this.threadLocalObject =localObject;
		
	}

}
