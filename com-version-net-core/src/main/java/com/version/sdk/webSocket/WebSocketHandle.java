package com.version.sdk.webSocket;

import com.version.common.entity.client.SuperClient;
import com.version.common.entity.client.WebSocketClient;
import com.version.common.manager.ServerSessionManager;
import com.version.common.util.LoggerUtil;
import com.version.sdk.common.IoSender;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * @Author 周希来
 * @Date 2019/9/2 14:50
 */
@ServerEndpoint(value = "/websocket/room")
@Component
public class WebSocketHandle {

    public WebSocketHandle(){

        LoggerUtil.info("初始化websocket");
    }
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        LoggerUtil.info("有连接加入，当前连接数为：{}");
        WebSocketClient WebSocketClient = new WebSocketClient(session,"websocket/room");
        // 将连接加入本地缓存管理器
        ServerSessionManager.getManager().putClient(WebSocketClient);
        LoggerUtil.info("初始化本地session"+session.toString());
        IoSender.sendWebsocketMsg(session,200,"链接成功");

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        SuperClient superClient = ServerSessionManager.getManager().findClientBySession(session);
        try {
            ServerSessionManager.getManager().closeClient(superClient);
        } catch (Exception e) {
            LoggerUtil.error("链接关闭异常");
            e.printStackTrace();
        }

    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     *            客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        LoggerUtil.info("来自客户端的消息：{}",message);
        IoSender.sendWebsocketMsg(session,200,"收到返回");

    }

    /**
     * 出现错误
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        LoggerUtil.error("发生错误：{}，Session ID： {}",error.getMessage(),session.getId());
        error.printStackTrace();
    }

}
