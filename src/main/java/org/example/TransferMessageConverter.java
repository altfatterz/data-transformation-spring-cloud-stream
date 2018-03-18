package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.MimeType;

import java.io.IOException;

public class TransferMessageConverter extends AbstractMessageConverter {

    private final ObjectMapper objectMapper;

    public TransferMessageConverter() {
        super(MimeType.valueOf("application/xml"));
        objectMapper = BankDataObjectMapperFactory.objectMapper();
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return clazz.equals(Transfer.class);
    }

    @Override
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, @Nullable Object conversionHint) {
        try {
            return objectMapper.readValue((byte[]) message.getPayload(), Transfer.class);
        } catch (IOException e) {
            return null;
        }
    }
}
