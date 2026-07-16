package betr.intern.spring_users.service;

import betr.intern.spring_users.model.UserStats;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class StatsWebSocketHandler extends TextWebSocketHandler {

  private static final Logger logger = LoggerFactory.getLogger(StatsWebSocketHandler.class);
  private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
  private final ObjectMapper objectMapper = new ObjectMapper();

  public StatsWebSocketHandler() {}

  @Override
  public void afterConnectionEstablished(final WebSocketSession session) {
    sessions.add(session);
    logger.info("WebSocket connection established: " + session.getId());
  }

  @Override
  public void afterConnectionClosed(
      final WebSocketSession session, final org.springframework.web.socket.CloseStatus status) {
    sessions.remove(session);
    logger.info("WebSocket connection closed: " + session.getId());
  }

  public void broadcastStats(final Map<String, UserStats> stats) {
    try {
      final String json = objectMapper.writeValueAsString(stats);
      final TextMessage message = new TextMessage(json);
      for (final WebSocketSession session : sessions) {
        if (session.isOpen()) {
          try {
            session.sendMessage(message);
          } catch (final IOException e) {
            logger.error("Failed to send WebSocket message", e);
          }
        }
      }
    } catch (final Exception e) {
      logger.error("Failed to serialize stats to JSON", e);
    }
  }
}
