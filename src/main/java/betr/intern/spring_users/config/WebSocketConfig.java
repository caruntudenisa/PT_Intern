package betr.intern.spring_users.config;

import betr.intern.spring_users.service.StatsWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

  private final StatsWebSocketHandler statsWebSocketHandler;

  public WebSocketConfig(final StatsWebSocketHandler statsWebSocketHandler) {
    this.statsWebSocketHandler = statsWebSocketHandler;
  }

  @Override
  public void registerWebSocketHandlers(final WebSocketHandlerRegistry registry) {
    registry.addHandler(statsWebSocketHandler, "/ws/stats").setAllowedOrigins("*");
  }
}
