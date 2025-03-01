package com.parkhere.reservation_service.repository;

import static com.parkhere.reservation_service.jooq.generated.Tables.PARKING_SPOTS;
import static com.parkhere.reservation_service.jooq.generated.Tables.RESERVATIONS;
import static java.lang.String.format;
import static org.jooq.impl.DSL.select;

import com.parkhere.reservation_service.controller.ReservationException;
import com.parkhere.reservation_service.jooq.generated.tables.records.ReservationsRecord;
import com.parkhere.reservation_service.model.ReservationRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {

  private final DSLContext create;

  public ReservationsRecord makeReservation(ReservationRequest request, Integer parkingLotId) {
    Condition startTimeOverlaps =
        RESERVATIONS
            .START_TIME
            .le(request.startTimestamp())
            .and(RESERVATIONS.END_TIME.ge(request.startTimestamp()));
    Condition endTimeOverlaps =
        RESERVATIONS
            .START_TIME
            .le(request.endTimestamp())
            .and(RESERVATIONS.END_TIME.ge(request.endTimestamp()));

    // first check if user already has reservation in the same time frame
    create
        .selectFrom(RESERVATIONS)
        .where(RESERVATIONS.USER_ID.eq(request.userId()))
        .and(startTimeOverlaps.or(endTimeOverlaps))
        .limit(1)
        .fetchOptional()
        .ifPresent(
            r -> {
              throw new ReservationException(
                  format(
                      "User %s already has a reservation in the same time frame.",
                      request.userId()));
            });

    // get the first available spot
    Optional<Integer> bestAvailableSpot =
        create
            .select(PARKING_SPOTS.SPOT_ID)
            .from(PARKING_SPOTS)
            .where(PARKING_SPOTS.PARKING_LOT_ID.eq(parkingLotId))
            .andNot(
                PARKING_SPOTS.SPOT_ID.in(
                    select(RESERVATIONS.SPOT_ID)
                        .from(RESERVATIONS)
                        .where(RESERVATIONS.PARKING_LOT_ID.eq(parkingLotId))
                        .and(startTimeOverlaps.or(endTimeOverlaps))))
            .orderBy(PARKING_SPOTS.PRIORITY.asc())
            .limit(1)
            .fetchOptional()
            .map(r -> r.into(Integer.class));

    if (bestAvailableSpot.isEmpty()) {
      throw new ReservationException(
          format("No available spots in parking lot %s in the given timeframe.", parkingLotId));
    }

    return create
        .insertInto(
            RESERVATIONS,
            RESERVATIONS.START_TIME,
            RESERVATIONS.END_TIME,
            RESERVATIONS.USER_ID,
            RESERVATIONS.SPOT_ID,
            RESERVATIONS.PARKING_LOT_ID)
        .values(
            request.startTimestamp(),
            request.endTimestamp(),
            request.userId(),
            bestAvailableSpot.get(),
            parkingLotId)
        .returning(
            RESERVATIONS.ID,
            RESERVATIONS.START_TIME,
            RESERVATIONS.END_TIME,
            RESERVATIONS.USER_ID,
            RESERVATIONS.SPOT_ID,
            RESERVATIONS.PARKING_LOT_ID)
        .fetchOptional()
        .map(r -> r.into(ReservationsRecord.class))
        .orElseThrow();
  }
}
