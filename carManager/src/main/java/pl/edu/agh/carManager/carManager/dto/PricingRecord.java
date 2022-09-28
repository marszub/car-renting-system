package pl.edu.agh.carManager.carManager.dto;

import javax.validation.constraints.NotNull;

public record PricingRecord(
        @NotNull
        Integer carType,//found in carDB

        @NotNull
        Integer price
) { }
