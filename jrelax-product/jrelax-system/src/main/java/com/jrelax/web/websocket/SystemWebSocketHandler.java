package com.jrelax.web.websocket;

import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengc on 2016-05-11.
 */
public class SystemWebSocketHandler implements WebSocketHandler {
    private static List<WebSocketSession> users = new ArrayList<WebSocketSession>();
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        users.add(webSocketSession);
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        System.out.println(webSocketMessage.getClass());
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if(webSocketSession.isOpen())
            webSocketSession.close();
        users.remove(webSocketSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        users.remove(webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给所有用户发送消息
     * @param msg
     */
    public void sendTextMessageToAllUser(TextMessage msg){
        for(WebSocketSession session : users){
            if(session.isOpen()){
                try {
                    session.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
