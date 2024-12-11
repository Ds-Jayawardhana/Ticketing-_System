package com.example.Backend.config;

import com.example.Backend.model.ActivityLog;
import com.example.Backend.model.TicketStatus;
import com.example.Backend.model.WebSocketMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

public class TicketWebHandler extends TextWebSocketHandler {
    private static final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session.getId());
    }

    public void broadcastActivity(ActivityLog activity) {
        WebSocketMessage message = new WebSocketMessage("ACTIVITY", activity);
        broadcast(message);
    }

    public void broadcastTicketStatus(TicketStatus status) {
        WebSocketMessage message = new WebSocketMessage("TICKET_STATUS", status);
        broadcast(message);
    }

    private void broadcast(WebSocketMessage message) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            TextMessage textMessage = new TextMessage(jsonMessage);

            for (WebSocketSession session : sessions.values()) {
                if (session.isOpen()) {
                    session.sendMessage(textMessage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}