package pl.edu.agh.tarrif.tarrif.dto;

import javax.validation.constraints.NotNull;

public record RentalData(
        @NotNull
        Integer reservationId,

        @NotNull
        String carId,

        @NotNull
        Long reservationTimestamp
) { }
