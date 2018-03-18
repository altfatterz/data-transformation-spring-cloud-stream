package org.example;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Currency;

public class CurrencyDeserializer extends StdDeserializer<Currency> {

    protected CurrencyDeserializer(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public Currency deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        return Currency.getInstance(parser.getValueAsString());
    }
}
