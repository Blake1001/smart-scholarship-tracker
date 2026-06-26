package org.example.strategy;

import org.example.api.CurrencyConverter;
import org.example.model.Applicant;
import org.example.model.EligibilityResult;

/**
 * Eligibility rule for need-based scholarships: the applicant qualifies
 * when their income, converted into the base currency, is at or below
 * the maximum allowed income.
 */
public class NeedBasedEligibilityStrategy implements EligibilityStrategy {

    private final double maxIncome;
    private final String baseCurrency;
    private final CurrencyConverter currencyConverter;

    /**
     * Creates a need-based rule.
     *
     * @param maxIncome         the highest income (in base currency) that still qualifies
     * @param baseCurrency      the currency all incomes are compared in (e.g. "USD")
     * @param currencyConverter used to convert the applicant's income into the base currency
     */
    public NeedBasedEligibilityStrategy(double maxIncome, String baseCurrency,
                                        CurrencyConverter currencyConverter) {
        this.maxIncome = maxIncome;
        this.baseCurrency = baseCurrency;
        this.currencyConverter = currencyConverter;
    }

    @Override
    public EligibilityResult evaluate(Applicant applicant) {
        double incomeInBase = currencyConverter.convert(
                applicant.getAnnualIncome(),
                applicant.getIncomeCurrency(),
                baseCurrency);

        if (incomeInBase <= maxIncome) {
            return new EligibilityResult(true,
                    "Income " + incomeInBase + " " + baseCurrency
                            + " is within the limit of " + maxIncome);
        }
        return new EligibilityResult(false,
                "Income " + incomeInBase + " " + baseCurrency
                        + " exceeds the limit of " + maxIncome);
    }
}