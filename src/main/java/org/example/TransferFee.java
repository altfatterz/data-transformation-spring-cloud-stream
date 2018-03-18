package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Currency;

@Getter
@AllArgsConstructor
public class TransferFee {

    private final BigDecimal amount;

    private final Currency currency;
}
