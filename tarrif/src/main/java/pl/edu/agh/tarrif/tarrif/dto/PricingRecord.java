package pl.edu.agh.tarrif.tarrif.dto;

import javax.validation.constraints.NotNull;

public record PricingRecord(
        @NotNull
        Integer carType,//found in carDB

        @NotNull
        Integer price
) { }
