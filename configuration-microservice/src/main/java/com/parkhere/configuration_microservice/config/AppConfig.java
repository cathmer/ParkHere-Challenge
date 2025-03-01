package com.parkhere.configuration_microservice.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkhere.configuration_microservice.model.Spot;
import com.parkhere.configuration_microservice.repository.ParkingLotRepository;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Value("${app.config.parking.lot.one}")
  private String parkingLotOne;

  @Value("${app.config.parking.lot.two}")
  private String parkingLotTwo;

  @Bean
  public ParkingLotRepository parkingLotRepository() throws IOException {
    return new ParkingLotRepository(getParkingLot(parkingLotOne), getParkingLot(parkingLotTwo));
  }

  private List<Spot> getParkingLot(String encodedParkingLot) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    String decodedParkingLot =
        new String(Base64.getDecoder().decode(encodedParkingLot), StandardCharsets.UTF_8).trim();
    return objectMapper
        .readerFor(new TypeReference<List<Spot>>() {})
        .readValue(objectMapper.readTree(decodedParkingLot));
  }
}
