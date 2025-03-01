package com.parkhere.reservation_service;

import com.parkhere.reservation_service.test.TestContainerInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = TestContainerInitializer.class)
class ReservationServiceApplicationTests {

  @Test
  void contextLoads() {}
}
