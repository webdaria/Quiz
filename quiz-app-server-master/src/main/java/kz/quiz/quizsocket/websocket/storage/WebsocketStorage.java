package kz.quiz.quizsocket.websocket.storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kz.quiz.quizsocket.data.model.UserSession;
import org.springframework.stereotype.Component;

@Component
public class WebsocketStorage {

  private final Map<String, UserSession> storageResult = new ConcurrentHashMap<>();

  public void add(String sessionId, UserSession userSession) {
    storageResult.put(sessionId, userSession);
  }

  public UserSession get(String sessionId) {
    return storageResult.get(sessionId);
  }

  public void delete(String sessionId) {
    storageResult.remove(sessionId);
  }

  public void update(String sessionId, UserSession userSession) {
    storageResult.put(sessionId, userSession);
  }
}
