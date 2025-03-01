package com.parkhere.reservation_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {

  @Bean
  public RestClient restClient() {
    return RestClient.builder().baseUrl("http://configuration-microservice:8080").build();
  }
}
