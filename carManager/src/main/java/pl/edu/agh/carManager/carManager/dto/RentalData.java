package pl.edu.agh.carManager.carManager.dto;

import javax.validation.constraints.NotNull;

public record RentalData(
        @NotNull
        Integer reservationId,

        @NotNull
        String carId,

        @NotNull
        Long reservationTimestamp
) { }
