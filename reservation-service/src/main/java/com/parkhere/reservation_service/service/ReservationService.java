package com.parkhere.reservation_service.service;

import com.parkhere.reservation_service.controller.ReservationException;
import com.parkhere.reservation_service.jooq.generated.tables.records.ReservationsRecord;
import com.parkhere.reservation_service.model.ParkingLot;
import com.parkhere.reservation_service.model.ReservationRequest;
import com.parkhere.reservation_service.model.ReservationResponse;
import com.parkhere.reservation_service.model.Spot;
import com.parkhere.reservation_service.repository.ParkingSpotRepository;
import com.parkhere.reservation_service.repository.ReservationRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class ReservationService {

  private final ParkingSpotRepository parkingSpotRepository;

  private final ReservationRepository reservationRepository;

  private final RestClient restClient;

  public ReservationResponse createReservation(ReservationRequest request, Integer parkingLotId) {
    if (request.endTimestamp() <= request.startTimestamp()) {
      throw new ReservationException("End time must be after start time.");
    }

    if (!parkingSpotRepository.parkingLotExists(parkingLotId)) {
      saveParkingLot(parkingLotId, getParkingLot(parkingLotId));
    }

    ReservationsRecord reservation = reservationRepository.makeReservation(request, parkingLotId);
    return new ReservationResponse(
        reservation.getId(),
        reservation.getSpotId(),
        reservation.getStartTime(),
        reservation.getEndTime());
  }

  public void saveParkingLot(Integer parkingLotId, List<Spot> spots) {
    parkingSpotRepository.saveParkingLot(new ParkingLot(parkingLotId, spots));
  }

  public List<Spot> getParkingLot(Integer parkingLotId) {
    Map<String, String> params = new HashMap<>();
    params.put("parkingLotId", parkingLotId.toString());
    return restClient
        .get()
        .uri("/api/parking-lots/{parkingLotId}", params)
        .retrieve()
        .body(new ParameterizedTypeReference<>() {});
  }
}
