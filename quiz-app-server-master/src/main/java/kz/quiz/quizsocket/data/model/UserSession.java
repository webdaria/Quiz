package kz.quiz.quizsocket.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSession {

  private String username;
  private int currentQuestionIndex;
  private int rightAnswers;
  private int wrongAnswers;

  public void incrementRightAnswers() {
    this.rightAnswers++;
  }

  public void decrementRightAnswers() {
    this.rightAnswers--;
  }

  public void incrementWrongAnswers() {
    this.wrongAnswers++;
  }

  public void decrementWrongAnswers() {
    this.wrongAnswers--;
  }

  public int nextQuestion() {
    return ++this.currentQuestionIndex;
  }
}
