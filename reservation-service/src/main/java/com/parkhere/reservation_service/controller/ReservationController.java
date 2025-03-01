package com.parkhere.reservation_service.controller;

import com.parkhere.reservation_service.model.ReservationRequest;
import com.parkhere.reservation_service.model.ReservationResponse;
import com.parkhere.reservation_service.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;

  @PostMapping("/api/parking-lots/{parkingLotId}/reservations")
  public ReservationResponse createReservation(
      @RequestBody ReservationRequest reservationRequest, @PathVariable Integer parkingLotId) {
    return reservationService.createReservation(reservationRequest, parkingLotId);
  }
}
