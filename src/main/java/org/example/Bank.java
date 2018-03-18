package org.example;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Bank {

    String TRANSFERS = "transfer";

    String TRANSFERS_WITH_FEE = "transfers-with-fee";

    @Input(Bank.TRANSFERS)
    SubscribableChannel transfers();

    @Output(Bank.TRANSFERS_WITH_FEE)
    MessageChannel transfersWithFee();

}
