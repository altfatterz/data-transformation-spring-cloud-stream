package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MessageConverter;

@SpringBootApplication
@EnableBinding(Bank.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @StreamMessageConverter
    public MessageConverter transferMessageConverter() {
        return new TransferMessageConverter();
    }
}
