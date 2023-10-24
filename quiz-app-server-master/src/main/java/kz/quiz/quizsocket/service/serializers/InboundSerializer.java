package kz.quiz.quizsocket.service.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import kz.quiz.quizsocket.data.messages.Inbound;
import org.springframework.stereotype.Component;

@Component
public class InboundSerializer extends JacksonSerializer<Inbound>{

  private final ObjectReader reader = mapper.readerFor(Inbound.class);
  private final ObjectWriter writer = mapper.writerFor(Inbound.class);

  @Override
  public String serializeToString(Inbound obj) throws JsonProcessingException {
    return writer.writeValueAsString(obj);
  }

  @Override
  public Inbound deserialize(String s) throws JsonProcessingException {
    return reader.readValue(s);
  }
}
