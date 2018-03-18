package org.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.cloud.stream.test.matcher.MessageQueueMatcher.receivesPayloadThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class TransferFeeCalculatorTests {

    @Autowired
    private Bank bank;

    @Autowired
    private MessageCollector collector;

    @Test
    public void testTransformer() {
        SubscribableChannel transfers = bank.transfers();

        Map<String, Object> headers = new HashMap<>();
        headers.put("contentType", "application/xml");

        String msg = "" +
                "<transfer>" +
                "   <amount>451</amount>" +
                "   <fromCurrency>EUR</fromCurrency>" +
                "   <toCurrency>CHF</toCurrency>" +
                "   <dateTime>2018-03-21T10:15:30</dateTime>" +
                "   <paymentMethod>CREDIT_CARD</paymentMethod>" +
                "</transfer>";

        transfers.send(new GenericMessage<>(msg.getBytes(StandardCharsets.UTF_8), headers));

        BlockingQueue<Message<?>> messages = this.collector.forChannel(bank.transfersWithFee());

        assertThat(messages, receivesPayloadThat(is(
                "{" +
                        "\"amount\":451," +
                        "\"fromCurrency\":\"EUR\"," +
                        "\"toCurrency\":\"CHF\"," +
                        "\"dateTime\":\"2018-03-21T10:15:30\"," +
                        "\"paymentMethod\":\"CREDIT_CARD\"," +
                        "\"transferFee\":{" +
                        "\"amount\":5.5924,\"" +
                        "currency\":\"EUR\"}" +
                        "}"
        )));

    }

}
