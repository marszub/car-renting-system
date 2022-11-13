package pl.edu.agh.rental.rental.dto;

import javax.validation.constraints.NotNull;

public record RentalData(
        @NotNull
        Integer reservationId,

        @NotNull
        Integer carId,

        @NotNull
        Long reservationTimestamp,

        @NotNull
        Long rentalCurrentTime,

        @NotNull
        Long rentalCost
) { }
