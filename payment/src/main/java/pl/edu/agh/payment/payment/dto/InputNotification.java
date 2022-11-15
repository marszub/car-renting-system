package pl.edu.agh.payment.payment.dto;

import javax.validation.constraints.NotNull;

//It will be deleted
public record InputNotification(
        @NotNull
        String paymentPayuId,

        @NotNull
        String changeValueTo
) { }
