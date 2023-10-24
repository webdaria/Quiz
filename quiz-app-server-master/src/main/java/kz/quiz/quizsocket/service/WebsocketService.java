package kz.quiz.quizsocket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import kz.quiz.quizsocket.data.messages.Inbound;
import org.springframework.web.socket.WebSocketSession;

public interface WebsocketService {

  void sendReady(WebSocketSession session) throws JsonProcessingException;

  void close(WebSocketSession session);

  void startAndSendQuestion(WebSocketSession session, Inbound inbound);

  void checkAnswerAndSendNextQuestion(WebSocketSession session, Inbound inbound);

  void finish(WebSocketSession session);
}
