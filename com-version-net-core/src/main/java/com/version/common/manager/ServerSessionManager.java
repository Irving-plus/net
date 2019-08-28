package com.version.common.manager;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

import com.version.common.annotation.IProcess;
import com.version.common.entity.AttributeKeyConst;
import com.version.common.entity.Controller;
import com.version.common.entity.client.SuperClient;
import com.version.common.entity.client.TcpSocketClient;
import com.version.common.entity.client.WebSocketClient;
import com.version.common.util.ConstantUtil;
import com.version.common.util.StringHelper;
import com.version.sdk.common.NetContext;
import com.version.service.api.INetEventService;

import io.netty.channel.Channel;

public class ServerSessionManager {
	public static String hostIp;
	public static String hostPort;
	public static long closeClientTime;
	private ServerSessionManager() {
	}

	private static ServerSessionManager serverSessionManager = new ServerSessionManager();

	public static ServerSessionManager getManager() {
		return serverSessionManager;
	}

	public final static Map<String, SuperClient> clients = new ConcurrentHashMap<String, SuperClient>();
	public final static Map<String, String> sessionIdPlayerIdMap = new ConcurrentHashMap<String, String>();
	private static final Object sessionLock = new Object();

	public void initServerManager() {
	}

	public void doHeartBeatCheck(long now) throws Exception {
		for (Entry<String, SuperClient> entry : clients.entrySet()) {
			long freshTime = (long) entry.getValue().getAttribute(ConstantUtil.LAST_RECIVED_TIME);
			if (now - freshTime > closeClientTime) {
				// 心跳超时,释放连接
				closeClient(entry.getValue());
			}
		}
	}

	public void putClient(SuperClient superClient) {
		synchronized (sessionLock) {
			String sessionId = superClient.getSessionId();
			superClient.setAttribute(ConstantUtil.VERSION_ONLINE_SESSION_UUID, sessionId);
			clients.put(sessionId, superClient);
		}
	}

	public void closeClient(SuperClient superClient) throws Exception {

		if (superClient instanceof TcpSocketClient) {
			TcpSocketClient tcpSocketClient = (TcpSocketClient) superClient;
			Channel session = tcpSocketClient.getSession();
			removeClient(superClient);
			session.close();
		} else if (superClient instanceof WebSocketClient) {
			WebSocketClient webSocketClient = (WebSocketClient) superClient;
			Session session = webSocketClient.getSession();
			removeClient(superClient);
			if (session.isOpen()) {
				session.close();
			}
		}
	}

	public void removeClient(SuperClient superClient) throws Exception {
		synchronized (sessionLock) {
			String sessionId = (String) superClient.getAttribute(ConstantUtil.VERSION_ONLINE_SESSION_UUID);
			if (StringHelper.isNotEmpty(sessionId)) {
				if (clients.containsKey(sessionId)) {
					clients.remove(sessionId);
					String playerId = sessionIdPlayerIdMap.remove(sessionId);
					if (StringHelper.isNotEmpty(playerId)) {
						Controller controller = CacheServer.getOnlineaccounts().get(playerId);
						if (controller != null) {
							CacheServer.getCache().offline(controller);
							superClient.removeAttribute(ConstantUtil.VERSION_LOGIN_CURRUSER);
							INetEventService eventService = NetContext.getInstance(INetEventService.class);
							eventService.frameSomeOneLoginOutEvent(controller);
						}
					}
				}
			}
		}
	}

	public void online(Controller controller, SuperClient superClient) throws Exception {
		boolean flag = false;
		String playerId = controller.getUniqueId();
		String sessionId = (String) superClient.getAttribute(ConstantUtil.VERSION_ONLINE_SESSION_UUID);
		if (sessionIdPlayerIdMap.containsValue(playerId)) {
			String oldSessionId = findSessionIdByPlayerId(playerId);
			if (StringHelper.isNotEmpty(oldSessionId)) {
				if (oldSessionId.equals(sessionId)) {
					// 自己重复调用登陆请求
					return;
				}
				// T出老的缓存
				SuperClient oldClient = clients.get(oldSessionId);
				if (oldClient != null) {
					closeClient(oldClient);
				}
			}
		}
		synchronized (sessionLock) {
			if (StringHelper.isNotEmpty(sessionId)) {
				controller.setSuperClient(superClient);
//				String info = packageServerInfo(playerId, controller);
//				superClient.setAttribute(ConstantUtil.VERSION_SESSION_REDIS_PALYERID + Config.SERVER_TYPE, playerId);// 绑定上线udid
				superClient.setAttribute(ConstantUtil.VERSION_LOGIN_CURRUSER, controller);
				if (clients.containsKey(sessionId)) {
					sessionIdPlayerIdMap.put(sessionId, playerId);
					CacheServer.getCache().online(controller);// 角色上线
//					jedis.hset(ConstantUtil.VERSION_SESSION_REDIS_PALYERID + Config.SERVER_TYPE, playerId, info);
					flag = true;
				}
			}
		}
		if (flag) {
			INetEventService netEventService = NetContext.getInstance(INetEventService.class);
			netEventService.frameSomeOneLoginSuccessEvent(controller);
		}
	}

	private String findSessionIdByPlayerId(String playerId) {
		for (Entry<String, String> entry : sessionIdPlayerIdMap.entrySet()) {
			if (entry.getValue().equals(playerId)) {
				return entry.getKey();
			}
		}
		return null;
	}

	public SuperClient findClientByPlayerId(String playerId) {
		String sessionId = findSessionIdByPlayerId(playerId);
		if (StringHelper.isNotEmpty(sessionId)) {
			SuperClient superClient = clients.get(sessionId);
			return superClient;
		}
		return null;
	}

	public SuperClient findClientBySessionId(String sessionId) {
		if (StringHelper.isNotEmpty(sessionId)) {
			SuperClient superClient = clients.get(sessionId);
			return superClient;
		}
		return null;
	}

	public SuperClient findClientBySession(Channel session) {
		String sessionId = session.attr(AttributeKeyConst.VERSION_UNIQUE_SESSION_PK_CLIENT).get();
		SuperClient superClient = findClientBySessionId(sessionId);
		return superClient;
	}

	public SuperClient findClientBySession(Session session) {
		String sessionId = session.getId();
		SuperClient superClient = findClientBySessionId(sessionId);
		return superClient;
	}

}
