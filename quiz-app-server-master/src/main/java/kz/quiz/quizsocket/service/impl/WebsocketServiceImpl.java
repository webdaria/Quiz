package kz.quiz.quizsocket.service.impl;

import java.io.IOException;
import kz.quiz.quizsocket.data.dto.QuestionDto;
import kz.quiz.quizsocket.data.enums.WebsocketEvent;
import kz.quiz.quizsocket.data.messages.Inbound;
import kz.quiz.quizsocket.data.messages.Outbound;
import kz.quiz.quizsocket.data.model.Question;
import kz.quiz.quizsocket.data.model.UserSession;
import kz.quiz.quizsocket.mapper.QuestionDtoMapper;
import kz.quiz.quizsocket.service.QuestionService;
import kz.quiz.quizsocket.service.WebsocketService;
import kz.quiz.quizsocket.service.serializers.OutboundSerializer;
import kz.quiz.quizsocket.websocket.storage.WebsocketStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebsocketServiceImpl implements WebsocketService {

  private final OutboundSerializer outboundSerializer;
  private final WebsocketStorage websocketStorage;
  private final QuestionService questionService;
  private final QuestionDtoMapper questionDtoMapper;

  @Override
  public void sendReady(WebSocketSession session) {
    try {
      session.sendMessage(new TextMessage(outboundSerializer.serializeToString(new Outbound(
          WebsocketEvent.READY, null, null))));
    } catch (IOException e) {
      log.error("Cannot send ready message, error message: {}", e.getMessage(), e);
    }
  }

  @Override
  public void close(WebSocketSession session) {
    try {
      if (session.isOpen()) {
        session.close();
      }
    } catch (IOException e) {
      log.error("Cannot close websocket, error message: {}", e.getMessage(), e);
    } finally {
      websocketStorage.delete(session.getId());
    }
  }

  @Override
  public void startAndSendQuestion(WebSocketSession session, Inbound inbound) {
    UserSession userSession = new UserSession();
    userSession.setUsername(inbound.getUsername());
    websocketStorage.add(session.getId(), userSession);
    sendQuestion(session, 0, userSession);
  }

  @Override
  public void checkAnswerAndSendNextQuestion(WebSocketSession session, Inbound inbound) {
    Question question = questionService.getQuestion(inbound.getQuestionIndex());
    UserSession userSession = websocketStorage.get(session.getId());

    if (question.isAnswerCorrect(inbound.getAnswer())) {
      userSession.incrementRightAnswers();
    } else {
      userSession.incrementWrongAnswers();
    }
    if (isLastQuestion(userSession)) {
      this.finish(session);
      return;
    }
    sendQuestion(session, userSession.nextQuestion(), userSession);
    websocketStorage.update(session.getId(), userSession);
  }

  @Override
  public void finish(WebSocketSession session) {
    UserSession userSession = websocketStorage.get(session.getId());
    try {
      session.sendMessage(new TextMessage(outboundSerializer.serializeToString(
          new Outbound(WebsocketEvent.FINISH, userSession, null))));
    } catch (IOException e) {
      log.error("Cannot send finish message, error message: {}", e.getMessage(), e);
    }
  }

  private void sendQuestion(WebSocketSession session, int index, UserSession userSession) {
    QuestionDto nextQuestion = questionDtoMapper.mapFromQuestion(
        questionService.getQuestion(index));
    try {
      session.sendMessage(new TextMessage(outboundSerializer.serializeToString(
          new Outbound(WebsocketEvent.QUESTION, userSession, nextQuestion))));
    } catch (IOException e) {
      log.error("Cannot send question, error message: {}", e.getMessage(), e);
    }
  }

  private boolean isLastQuestion(UserSession userSession) {
    return userSession.getCurrentQuestionIndex() == questionService.numberOfQuestions();
  }

}
