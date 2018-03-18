package org.example;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Data
@ToString
public class Transfer {

    private BigDecimal amount;

    private Currency fromCurrency;

    private Currency toCurrency;

    private LocalDateTime dateTime;

    private PaymentMethod paymentMethod;

}
