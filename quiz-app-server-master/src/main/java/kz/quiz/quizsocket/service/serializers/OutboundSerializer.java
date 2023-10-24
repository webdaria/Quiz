package kz.quiz.quizsocket.service.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import kz.quiz.quizsocket.data.messages.Inbound;
import kz.quiz.quizsocket.data.messages.Outbound;
import org.springframework.stereotype.Component;

@Component
public class OutboundSerializer extends JacksonSerializer<Outbound>{

  private final ObjectReader reader = mapper.readerFor(Outbound.class);
  private final ObjectWriter writer = mapper.writerFor(Outbound.class);

  @Override
  public String serializeToString(Outbound obj) throws JsonProcessingException {
    return writer.writeValueAsString(obj);
  }

  @Override
  public Outbound deserialize(String s) throws JsonProcessingException {
    return reader.readValue(s);
  }
}
