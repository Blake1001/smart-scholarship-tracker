package org.example.gui;

import org.example.api.CurrencyConverter;
import org.example.model.Scholarship;
import org.example.strategy.MeritAndNeedEligibilityStrategy;
import org.example.strategy.MeritEligibilityStrategy;
import org.example.strategy.NeedBasedEligibilityStrategy;

/**
 * Builds a Scholarship (with the correct eligibility rule) from the name the
 * user picked in the form. Keeping this here means the form does not need to
 * know how the rules are constructed.
 */
public class ScholarshipFactory {

    private static final double MIN_GPA = 3.5;
    private static final double MAX_INCOME = 6000.0;
    private static final String BASE_CURRENCY = "USD";

    private final CurrencyConverter currencyConverter;

    /**
     * Creates the factory.
     *
     * @param currencyConverter used by the need-based rules
     */
    public ScholarshipFactory(CurrencyConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
    }

    /**
     * Builds a scholarship for the given display name.
     *
     * @param name the scholarship name chosen in the form
     * @return a Scholarship carrying the matching eligibility rule
     */
    public Scholarship create(String name) {
        MeritEligibilityStrategy meritRule = new MeritEligibilityStrategy(MIN_GPA);
        NeedBasedEligibilityStrategy needRule =
                new NeedBasedEligibilityStrategy(MAX_INCOME, BASE_CURRENCY, currencyConverter);

        return switch (name) {
            case "Need-Based Scholarship" ->
                    new Scholarship("S002", name, needRule);
            case "Merit + Need Scholarship" ->
                    new Scholarship("S003", name,
                            new MeritAndNeedEligibilityStrategy(meritRule, needRule));
            default ->
                    new Scholarship("S001", name, meritRule);
        };
    }
}