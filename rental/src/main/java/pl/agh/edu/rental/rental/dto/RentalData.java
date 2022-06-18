package pl.agh.edu.rental.rental.dto;

import javax.validation.constraints.NotNull;

public record RentalData(
        @NotNull
        Integer reservationId,

        @NotNull
        Integer carId,

        @NotNull
        Long reservationTimestamp
) { }
