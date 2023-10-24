package kz.quiz.quizsocket.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionDto {

  private String question;

  private String answerA;

  private String answerB;

  private String answerC;

  private String answerD;
}
