package org.example;

import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.Currency;

public class BankDataModule extends SimpleModule {

    public BankDataModule() {
        super("BankDataModule");

        addDeserializer(Currency.class, new CurrencyDeserializer(Currency.class));
    }
}
