package pl.edu.agh.rental.rental.dto;

import javax.validation.constraints.NotNull;

public record RentalCreateInput(
        @NotNull
        Integer carId,
        @NotNull
        Integer carTypeId
) { }
