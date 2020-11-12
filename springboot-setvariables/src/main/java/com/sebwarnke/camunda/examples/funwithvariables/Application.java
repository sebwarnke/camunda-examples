package com.sebwarnke.camunda.examples.funwithvariables;

import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Map;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class);
  }

  @Bean
  public JavaDelegate printVariableNames() {
    return delegateExecution -> {
      Map<String, Object> variables = delegateExecution.getVariables();
      variables.keySet().stream().forEach(System.out::println);
    };
  }

}