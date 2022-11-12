package pl.edu.agh.payment.payment.dto;

import pl.edu.agh.payment.payment.persistence.Payment;
import pl.edu.agh.payment.payment.persistence.PaymentStatus;

import javax.validation.constraints.NotNull;

public record PaymentData(
        @NotNull
        Integer id,

        @NotNull
        Integer rentalId,

        @NotNull
        PaymentStatus paymentStatus
)
{
        public PaymentData(final Payment payment) {
                this(payment.getId(), payment.getRentalId(), payment.getPaymentStatus());
        }
}
