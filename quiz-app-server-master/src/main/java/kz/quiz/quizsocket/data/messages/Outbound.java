package kz.quiz.quizsocket.data.messages;

import kz.quiz.quizsocket.data.dto.QuestionDto;
import kz.quiz.quizsocket.data.enums.WebsocketEvent;
import kz.quiz.quizsocket.data.model.UserSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Outbound {

  private WebsocketEvent event;
  private UserSession userSession;
  private QuestionDto questionDto;
}
