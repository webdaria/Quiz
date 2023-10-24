package kz.quiz.quizsocket.service;

import kz.quiz.quizsocket.data.dto.QuestionDto;
import kz.quiz.quizsocket.data.messages.Inbound;
import kz.quiz.quizsocket.data.model.Question;

public interface QuestionService {

  Question getQuestion(int index);

  int numberOfQuestions();
}
