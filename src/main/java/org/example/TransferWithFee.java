package org.example;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Builder
@Getter
public class TransferWithFee {

    private BigDecimal amount;

    private Currency fromCurrency;

    private Currency toCurrency;

    private LocalDateTime dateTime;

    private PaymentMethod paymentMethod;

    private TransferFee transferFee;
}
