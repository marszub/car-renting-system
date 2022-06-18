package pl.agh.edu.carRentingMicroservice.reservation.dto;

import javax.validation.constraints.NotNull;

public record ReservationCreateInput(
        @NotNull
        Integer carId
) { }
