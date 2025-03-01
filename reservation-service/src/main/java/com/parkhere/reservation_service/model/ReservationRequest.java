package com.parkhere.reservation_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReservationRequest(
    @JsonProperty("user-id") String userId, Long startTimestamp, Long endTimestamp) {}
