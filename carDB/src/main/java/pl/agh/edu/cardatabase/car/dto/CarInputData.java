package pl.agh.edu.cardatabase.car.dto;

import javax.validation.constraints.NotNull;

public record CarInputData(
        @NotNull
        String name
) { }
