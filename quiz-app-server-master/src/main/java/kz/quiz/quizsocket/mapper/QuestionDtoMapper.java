package kz.quiz.quizsocket.mapper;

import kz.quiz.quizsocket.data.model.Question;
import kz.quiz.quizsocket.data.dto.QuestionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionDtoMapper {

  QuestionDto mapFromQuestion(Question question);
}
