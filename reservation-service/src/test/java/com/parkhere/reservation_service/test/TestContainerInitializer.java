package com.parkhere.reservation_service.test;

import jakarta.annotation.Nonnull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class TestContainerInitializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  private static final PostgreSQLContainer<?> postgreSQLContainer =
      new PostgreSQLContainer<>("postgres:17.1-alpine");

  @Override
  public void initialize(@Nonnull ConfigurableApplicationContext applicationContext) {
    initializePostgres(applicationContext);
  }

  private void initializePostgres(ConfigurableApplicationContext applicationContext) {
    if (!postgreSQLContainer.isRunning()) {
      postgreSQLContainer.waitingFor(Wait.forListeningPort()).start();
    }

    System.out.println("PostgreSQL URL: " + postgreSQLContainer.getJdbcUrl());

    TestPropertyValues.of(
            "spring.datasource.url = " + postgreSQLContainer.getJdbcUrl(),
            "spring.datasource.username = " + postgreSQLContainer.getUsername(),
            "spring.datasource.password = " + postgreSQLContainer.getPassword())
        .applyTo(applicationContext.getEnvironment());
  }
}
