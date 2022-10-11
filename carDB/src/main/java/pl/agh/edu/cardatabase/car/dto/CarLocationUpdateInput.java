package pl.agh.edu.cardatabase.car.dto;

import javax.validation.constraints.NotNull;

public record CarLocationUpdateInput(
        @NotNull
        Coordinates coordinates
) { }
