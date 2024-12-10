package com.example.Backend.config;

import com.example.Backend.model.ActivityLog;
import com.example.Backend.model.TicketStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class TicketWebHandler extends TextWebSocketHandler {
    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session.getId());
    }

    public void broadcastTicketStatus(TicketStatus status) {
        broadcast(new WebSocketMessage("TICKET_STATUS", status));
    }

    public void broadcastActivity(ActivityLog activity) {
        broadcast(new WebSocketMessage("ACTIVITY", activity));
    }

    private void broadcast(WebSocketMessage message) {
        String payload;
        try {
            payload = objectMapper.writeValueAsString(message);
            TextMessage textMessage = new TextMessage(payload);

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