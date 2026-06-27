package org.example.api;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrencyConverterTest {

    @Test
    void convert_sameCurrency_returnsSameAmount() {
        FrankfurterCurrencyConverter converter = new FrankfurterCurrencyConverter();
        assertEquals(100.0, converter.convert(100.0, "USD", "USD"));
    }

    @Test
    void frankfurterResponse_parsedFromJson_exposesRate() {
        String json = "{\"amount\":1.0,\"base\":\"USD\",\"date\":\"2026-06-25\",\"rates\":{\"MYR\":4.1175}}";
        FrankfurterResponse response = new Gson().fromJson(json, FrankfurterResponse.class);
        assertAll("parsed response",
                () -> assertEquals("USD", response.getBase()),
                () -> assertEquals("2026-06-25", response.getDate()),
                () -> assertEquals(4.1175, response.getRates().get("MYR"))
        );
    }

    @Test
    void currencyConversionException_carriesMessage() {
        CurrencyConversionException ex = new CurrencyConversionException("service down", null);
        assertEquals("service down", ex.getMessage());
    }
}