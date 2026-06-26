package org.example.api;

/**
 * Thrown when a currency conversion fails, for example when the exchange-rate
 * service is unreachable or returns an error response.
 */
public class CurrencyConversionException extends RuntimeException {

    /**
     * Creates the exception with a readable message and the original cause.
     *
     * @param message what went wrong, in plain language
     * @param cause   the underlying technical error
     */
    public CurrencyConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}