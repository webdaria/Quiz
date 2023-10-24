package kz.quiz.quizsocket.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import kz.quiz.quizsocket.data.enums.AnswerOptions;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Question {

  private String question;

  @JsonProperty(value = "A")
  private String answerA;

  @JsonProperty(value = "B")
  private String answerB;

  @JsonProperty(value = "C")
  private String answerC;

  @JsonProperty(value = "D")
  private String answerD;

  @JsonProperty(value = "answer")
  private AnswerOptions correctOption;

  @Override
  public String toString() {
    return "Question{" +
        "question='" + question + '\'' +
        ", answerA='" + answerA + '\'' +
        ", answerB='" + answerB + '\'' +
        ", answerC='" + answerC + '\'' +
        ", answerD='" + answerD + '\'' +
        ", correctOption='" + correctOption + '\'' +
        '}';
  }

  public boolean isAnswerCorrect(AnswerOptions option) {
    return this.correctOption.equals(option);
  }
}
