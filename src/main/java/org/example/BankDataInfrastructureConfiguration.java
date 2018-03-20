package org.example;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MessageConverter;

@EnableBinding(Bank.class)
public class BankDataInfrastructureConfiguration {

    @Bean
    @StreamMessageConverter
    public MessageConverter transferMessageConverter() {
        return new TransferMessageConverter();
    }
}
