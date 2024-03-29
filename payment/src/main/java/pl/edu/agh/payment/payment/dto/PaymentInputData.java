package pl.edu.agh.payment.payment.dto;

import javax.validation.constraints.NotNull;

public record PaymentInputData(
        @NotNull
        String email,

        @NotNull
        Integer rentalId
) { }
