package org.example.api;

import java.util.Map;

/**
 * Matches the shape of the JSON returned by the Frankfurter API, for example:
 * {"amount":1.0,"base":"USD","date":"2026-06-25","rates":{"MYR":4.1175}}
 * Gson fills these fields automatically from the JSON.
 */
public class FrankfurterResponse {
    private double amount;
    private String base;
    private String date;
    private Map<String, Double> rates;

    public double getAmount() {
        return amount;
    }

    public String getBase() {
        return base;
    }

    public String getDate() {
        return date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }
}