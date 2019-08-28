package com.version.common.manager;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.version.common.annotation.IProcess;
import com.version.common.annotation.TCPController;
import com.version.common.find.DynamicFind;

public class TcpControllerManager extends DynamicFind {
	private static final Map<String, Class<?>> controllerClazzMap = new ConcurrentHashMap<String, Class<?>>();
	private static final Map<Integer, Method>  methodMap = new ConcurrentHashMap<Integer, Method>();

	private TcpControllerManager() {
	}

	private static TcpControllerManager processManager = new TcpControllerManager();

	public static TcpControllerManager getManager() {
		return processManager;
	}

	public boolean verification(Class<?> clazz) {
		return annotationOn(clazz, TCPController.class);
	}

	@Override
	public void findClass(Class<?> clz) throws Exception {
		TCPController controller = clz.getAnnotation(TCPController.class);
		String name = controller.name();
		if(controllerClazzMap.containsKey(name)) {
			throw new Exception("扫描出重复的TcpController:"+name);
		}
		
		
		controllerClazzMap.put(name, clz);
	}

	public Class<?> getProcess(String name) {
		return controllerClazzMap.get(name);
	}

	@Override
	public void afterFind() {
	}

	@Override
	public void beforeFind() {
	}
}
