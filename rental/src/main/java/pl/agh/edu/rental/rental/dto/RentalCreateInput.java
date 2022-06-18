package pl.agh.edu.rental.rental.dto;

import javax.validation.constraints.NotNull;

public record RentalCreateInput(
        @NotNull
        Integer carId
) { }
