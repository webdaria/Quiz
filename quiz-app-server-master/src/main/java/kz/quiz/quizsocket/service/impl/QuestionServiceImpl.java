package kz.quiz.quizsocket.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import kz.quiz.quizsocket.mapper.QuestionDtoMapper;
import kz.quiz.quizsocket.data.model.Question;
import kz.quiz.quizsocket.service.QuestionService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

  private final List<Question> questions;

  private final ObjectMapper objectMapper;
  private final QuestionDtoMapper questionDtoMapper;

  public QuestionServiceImpl(ObjectMapper objectMapper, ObjectMapper objectMapper1,
      QuestionDtoMapper questionDtoMapper) throws IOException {
    this.objectMapper = objectMapper1;
    this.questionDtoMapper = questionDtoMapper;
    ClassPathResource classPathResource = new ClassPathResource("questions/questions.json");
    this.questions = objectMapper.readValue(classPathResource.getContentAsByteArray(),
        new TypeReference<>() {});
  }

  @Override
  public Question getQuestion(int index) {
    return questions.get(index);
  }

  @Override
  public int numberOfQuestions() {
    return questions.size();
  }

}
