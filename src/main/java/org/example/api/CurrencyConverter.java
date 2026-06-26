package org.example.api;

/**
 * Converts a money amount from one currency to another.
 * This is only the contract; the real implementation that calls the
 * exchange-rate service is added later. Depending on this interface
 * (not a concrete class) lets the rules be tested and swapped easily.
 */
public interface CurrencyConverter {

    /**
     * Converts an amount between two currencies.
     *
     * @param amount       the money amount to convert
     * @param fromCurrency the currency the amount is in (e.g. "MYR")
     * @param toCurrency   the currency to convert to (e.g. "USD")
     * @return the converted amount in the target currency
     */
    double convert(double amount, String fromCurrency, String toCurrency);
}