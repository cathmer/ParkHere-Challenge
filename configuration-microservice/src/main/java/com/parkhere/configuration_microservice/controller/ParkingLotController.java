package com.parkhere.configuration_microservice.controller;

import com.parkhere.configuration_microservice.model.Spot;
import com.parkhere.configuration_microservice.repository.ParkingLotRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ParkingLotController {

  private final ParkingLotRepository parkingLotRepository;

  @GetMapping("/api/parking-lots/{id}")
  public List<Spot> parkingLot(@PathVariable Integer id) {
    return parkingLotRepository.getParkingLot(id);
  }
}
