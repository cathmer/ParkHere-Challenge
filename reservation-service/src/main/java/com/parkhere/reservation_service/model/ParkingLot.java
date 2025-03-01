package com.parkhere.reservation_service.model;

import java.util.List;

public record ParkingLot(Integer parkingLotId, List<Spot> spots) {}
