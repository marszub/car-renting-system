package pl.edu.agh.carManager.carManager.dto;

import javax.validation.constraints.NotNull;

public record CarInput(
        @NotNull
        Integer carId,

        @NotNull
        String carToken
) { }
