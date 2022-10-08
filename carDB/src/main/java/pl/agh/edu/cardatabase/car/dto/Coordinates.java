package pl.agh.edu.cardatabase.car.dto;

import javax.validation.constraints.NotNull;

public record Coordinates(
        @NotNull
        Double latitude,

        @NotNull
        Double longitude
) { }
