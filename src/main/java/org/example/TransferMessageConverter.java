package org.example;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.MimeType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransferMessageConverter extends AbstractMessageConverter {

    private final ObjectMapper objectMapper;

    public TransferMessageConverter() {
        super(MimeType.valueOf("application/xml"));
        objectMapper = new XmlMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
        objectMapper.registerModule(javaTimeModule);

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
