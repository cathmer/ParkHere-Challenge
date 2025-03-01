package com.parkhere.reservation_service.repository;

import static com.parkhere.reservation_service.jooq.generated.Tables.PARKING_SPOTS;

import com.parkhere.reservation_service.model.ParkingLot;
import com.parkhere.reservation_service.model.Spot;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ParkingSpotRepository {

  private final DSLContext create;

  public void saveParkingLot(ParkingLot parkingLot) {
    for (Spot spot : parkingLot.spots()) {
      create
          .insertInto(
              PARKING_SPOTS,
              PARKING_SPOTS.SPOT_ID,
              PARKING_SPOTS.SPOT_NAME,
              PARKING_SPOTS.PARKING_LOT_ID,
              PARKING_SPOTS.PRIORITY)
          .values(spot.id(), spot.name(), parkingLot.parkingLotId(), spot.priority())
          .execute();
    }
  }

  public boolean parkingLotExists(Integer parkingLotId) {
    return create.fetchExists(
        create
            .selectFrom(PARKING_SPOTS)
            .where(PARKING_SPOTS.PARKING_LOT_ID.eq(parkingLotId))
            .limit(1));
  }
}
