package pl.agh.edu.carRentingMicroservice.reservation.dto;

import javax.validation.constraints.NotNull;

public record ReservationData(
        @NotNull
        Integer reservationId,

        @NotNull
        Integer carId,

        @NotNull
        Long reservationTimestamp
)
{ }
