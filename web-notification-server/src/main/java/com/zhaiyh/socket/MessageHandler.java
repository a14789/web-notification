package com.zhaiyh.socket;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;

@Component
public class MessageHandler extends TextWebSocketHandler {

    @Autowired
    private MessageService messageService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        RegisterMsg msg = JSON.parseObject(message.getPayload(), RegisterMsg.class);
        for (String tag : msg.getTags()) {
            messageService.addTagSession(tag, session);
        }
        messageService.addUserSession(msg.getUserId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class RegisterMsg {
        private String userId;
        private List<String> tags;
    }
}
