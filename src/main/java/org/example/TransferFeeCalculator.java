package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Currency;

@Component
@Slf4j
public class TransferFeeCalculator {

    @ServiceActivator(inputChannel = Bank.TRANSFERS, outputChannel = Bank.TRANSFERS_WITH_FEE)
    public TransferWithFee transform(Transfer transfer) {
        log.info("received transfer message: " + transfer);

        TransferWithFee transferWithFee = TransferWithFee.builder()
                .amount(transfer.getAmount())
                .fromCurrency(transfer.getFromCurrency())
                .toCurrency(transfer.getToCurrency())
                .dateTime(transfer.getDateTime())
                .paymentMethod(transfer.getPaymentMethod())
                .transferFee(calculateTransferFee(
                        transfer.getAmount(), transfer.getFromCurrency(), transfer.getPaymentMethod()))
                .build();

        return transferWithFee;
    }

    private TransferFee calculateTransferFee(BigDecimal amount, Currency currency, PaymentMethod paymentMethod) {
        TransferFee transferFee = null;
        switch (paymentMethod) {
            case BANK_TRANSFER:
                transferFee = new TransferFee(BigDecimal.ZERO, currency);
                break;
            case DEBIT_CARD:
                transferFee = new TransferFee(amount.multiply(
                        BigDecimal.valueOf(1.19).divide(BigDecimal.valueOf(100))), currency);
                break;
            case CREDIT_CARD:
                transferFee = new TransferFee(amount.multiply(BigDecimal.valueOf(1.24)
                        .divide(BigDecimal.valueOf(100))), currency);
                break;
            default:
                throw new IllegalArgumentException("unknown payment method " + paymentMethod);
        }
        return transferFee;
    }
}
