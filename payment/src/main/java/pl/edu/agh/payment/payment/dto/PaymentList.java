package pl.edu.agh.payment.payment.dto;

import java.util.List;

public record PaymentList(
        List<PaymentData> payments
) { }
