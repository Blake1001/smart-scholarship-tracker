package org.example.api;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * A CurrencyConverter that fetches live exchange rates from the Frankfurter API
 * (https://api.frankfurter.dev). It reads the JSON response, extracts the rate,
 * and converts the amount. It handles success, error responses, and an
 * unreachable service without exposing technical errors to the caller.
 */
public class FrankfurterCurrencyConverter implements CurrencyConverter {

    private static final String API_BASE_URL = "https://api.frankfurter.dev/v1/latest";
    private static final int HTTP_OK = 200;
    private static final int TIMEOUT_SECONDS = 5;

    private final HttpClient httpClient;
    private final Gson gson;

    /** Creates a converter with a default HTTP client. */
    public FrankfurterCurrencyConverter() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    @Override
    public double convert(double amount, String fromCurrency, String toCurrency) {
        // No conversion needed when both currencies are the same.
        if (fromCurrency.equalsIgnoreCase(toCurrency)) {
            return amount;
        }

        String url = API_BASE_URL + "?base=" + fromCurrency + "&symbols=" + toCurrency;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != HTTP_OK) {
                throw new CurrencyConversionException(
                        "Exchange-rate service returned status " + response.statusCode(), null);
            }

            FrankfurterResponse parsed = gson.fromJson(response.body(), FrankfurterResponse.class);
            if (parsed == null || parsed.getRates() == null
                    || !parsed.getRates().containsKey(toCurrency)) {
                throw new CurrencyConversionException(
                        "No exchange rate found for " + fromCurrency + " to " + toCurrency, null);
            }

            double rate = parsed.getRates().get(toCurrency);
            return amount * rate;

        } catch (CurrencyConversionException e) {
            throw e;
        } catch (Exception e) {
            throw new CurrencyConversionException(
                    "Could not reach the exchange-rate service", e);
        }
    }
}