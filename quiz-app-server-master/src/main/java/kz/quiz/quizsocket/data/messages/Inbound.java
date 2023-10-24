package kz.quiz.quizsocket.data.messages;

import kz.quiz.quizsocket.data.enums.AnswerOptions;
import kz.quiz.quizsocket.data.enums.WebsocketEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inbound {

  private String username;
  private WebsocketEvent event;
  private int questionIndex;
  private AnswerOptions answer;
}
