package kz.quiz.quizsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@EnableWebSocket
@SpringBootApplication
public class QuizSocketApplication {

  public static void main(String[] args) {
    SpringApplication.run(QuizSocketApplication.class, args);
  }

}
