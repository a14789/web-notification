package com.zhaiyh.socket;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
public class MessageService {

    private static final Map<String, Set<WebSocketSession>> TAG_SESSION_MAP = new ConcurrentHashMap<>();
    private static final Map<String, Set<WebSocketSession>> USER_SESSION_MAP = new ConcurrentHashMap<>();


    public void addTagSession(String tag, WebSocketSession session) {
        Set<WebSocketSession> sessions = TAG_SESSION_MAP.get(tag);
        if (sessions == null) {
            sessions = new CopyOnWriteArraySet<>();
        }
        sessions.add(session);
        TAG_SESSION_MAP.put(tag, sessions);
    }

    public void addUserSession(String userId, WebSocketSession session) {
        Set<WebSocketSession> sessions = USER_SESSION_MAP.get(userId);
        if (sessions == null) {
            sessions = new CopyOnWriteArraySet<>();
        }
        sessions.add(session);
        USER_SESSION_MAP.put(userId, sessions);
    }

    public void sendToTag(List<String> tags, String msg) {
        if (tags == null) {
            return;
        }

        tags.forEach(tag -> {
            Set<WebSocketSession> sessions = TAG_SESSION_MAP.get(tag);
            send(sessions, msg);
        });
    }

    public void sendToUser(List<String> users, String msg) {
        if (users == null) {
            return;
        }

        users.forEach(tag -> {
            Set<WebSocketSession> sessions = USER_SESSION_MAP.get(tag);
            send(sessions, msg);
        });
    }

    public void send(Collection<WebSocketSession> sessions, String msg) {
        List<WebSocketSession> removeList = new ArrayList<>();
        if (sessions != null) {
            sessions.forEach(session -> {
                try {
                    if (session.isOpen()) {
                        session.sendMessage(new TextMessage(msg));
                    } else {
                        removeList.add(session);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            sessions.removeAll(removeList);
        }
    }

}
