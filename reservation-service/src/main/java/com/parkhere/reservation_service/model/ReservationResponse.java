package com.parkhere.reservation_service.model;

public record ReservationResponse(
    Integer id, Integer spotId, Long startTimestamp, Long endTimestamp) {}
