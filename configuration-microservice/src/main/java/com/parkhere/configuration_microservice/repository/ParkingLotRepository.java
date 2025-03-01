package com.parkhere.configuration_microservice.repository;

import com.parkhere.configuration_microservice.model.Spot;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLotRepository {

  private final Map<Integer, List<Spot>> parkingLots = new HashMap<>();

  public ParkingLotRepository(List<Spot> lotOne, List<Spot> lotTwo) {
    parkingLots.put(1, lotOne);
    parkingLots.put(2, lotTwo);
  }

  public List<Spot> getParkingLot(int lotId) {
    return parkingLots.get(lotId);
  }
}
