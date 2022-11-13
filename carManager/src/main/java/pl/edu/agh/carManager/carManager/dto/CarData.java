package pl.edu.agh.carManager.carManager.dto;

import javax.validation.constraints.NotNull;

public record CarData(
    @NotNull
    Integer carID,

    @NotNull
    String token//possible change of type


){}
