package kz.quiz.quizsocket.websocket.config;

import java.util.concurrent.TimeUnit;
import kz.quiz.quizsocket.websocket.handler.WebsocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@RequiredArgsConstructor
public class WebsocketConfig implements WebSocketConfigurer {

  private final WebsocketHandler websocketHandler;

  @Value("${application.ws.path}")
  private String path;
  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry
        .addHandler(websocketHandler, path)
        .setHandshakeHandler(handshakeHandler())
        .setAllowedOrigins("*");

  }

  @Bean
  public ServletServerContainerFactoryBean containerFactoryBean() {
    ServletServerContainerFactoryBean containerFactoryBean = new ServletServerContainerFactoryBean();
    containerFactoryBean.setMaxBinaryMessageBufferSize(10);
    containerFactoryBean.setMaxSessionIdleTimeout(TimeUnit.SECONDS.toMillis(100));
    return containerFactoryBean;
  }

  public DefaultHandshakeHandler handshakeHandler() {
    return new DefaultHandshakeHandler(new TomcatRequestUpgradeStrategy());
  }
}
