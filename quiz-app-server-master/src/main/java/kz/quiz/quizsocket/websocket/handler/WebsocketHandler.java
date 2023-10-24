package kz.quiz.quizsocket.websocket.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.concurrent.Semaphore;
import kz.quiz.quizsocket.data.messages.Inbound;
import kz.quiz.quizsocket.service.WebsocketService;
import kz.quiz.quizsocket.service.serializers.InboundSerializer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebsocketHandler extends AbstractWebSocketHandler {

  private final Semaphore mutex = new Semaphore(Runtime.getRuntime().availableProcessors());

  private final WebsocketService websocketService;
  private final InboundSerializer inboundSerializer;

  @Override
  public void afterConnectionEstablished(@NonNull WebSocketSession session) {
    try {
      mutex.acquire();
      websocketService.sendReady(session);
      log.info("Remaining number of cores: {}", mutex.availablePermits());
    } catch (InterruptedException | JsonProcessingException e) {
      log.error("Error when sending ready message, error message: {}", e.getMessage());
      try {
        session.sendMessage(new TextMessage(""));
      } catch (IOException ex) {
        log.error("Cannot send message, error message: {}", e.getMessage(), e);
      }
    }
  }

  @Override
  protected void handleTextMessage(@NonNull WebSocketSession session,
      @NonNull TextMessage message) {
    try {
      Inbound inbound = inboundSerializer.deserialize(message.getPayload());
      switch (inbound.getEvent()) {
        case START -> websocketService.startAndSendQuestion(session, inbound);
        case ANSWER -> websocketService.checkAnswerAndSendNextQuestion(session, inbound);
        case FINISH -> websocketService.finish(session);
      }
    } catch (JsonProcessingException e) {

    }
  }

  @Override
  public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
    try {
      websocketService.close(session);
    } finally {
      mutex.release();
    }
  }
}
