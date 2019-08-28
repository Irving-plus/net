package com.version.common.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.version.common.entity.Controller;

public class CacheServer {
	private static final Map<Object, Controller> onlineAccounts = new ConcurrentHashMap<Object, Controller>();
	private static final Map<Object, Controller> offlineAccounts = new ConcurrentHashMap<Object, Controller>();
	private static final CacheServer cache = new CacheServer();

	private CacheServer() {
	}

	public static CacheServer getCache() {
		return cache;
	}

	public static Map<Object, Controller> getOnlineaccounts() {
		return onlineAccounts;
	}

	public void online(Controller controller) {
		String id = controller.getUniqueId();
		onlineAccounts.put(id, controller);
		offlineAccounts.remove(id);
	}

	public void offline(Controller controller) {
		String id = controller.getUniqueId();
		// 设置最后一次登出时间
		controller.setLastLoginOutTime(System.currentTimeMillis());
		// 加入离线缓存
		offlineAccounts.put(id, controller);
		// 移除在线缓存
		onlineAccounts.remove(id);
	}

	public static Map<Object, Controller> getOfflineaccounts() {
		return offlineAccounts;
	}

}
