package org.example;

import org.example.api.CurrencyConverter;
import org.example.api.FrankfurterCurrencyConverter;
import org.example.model.Applicant;
import org.example.model.EligibilityResult;
import org.example.model.Scholarship;
import org.example.strategy.NeedBasedEligibilityStrategy;

/**
 * Temporary entry point used to try the live currency converter by hand.
 * It will be replaced by the graphical interface in a later phase.
 */
public class Main {

    public static void main(String[] args) {
        CurrencyConverter converter = new FrankfurterCurrencyConverter();

        // Quick direct test of the converter.
        double usd = converter.convert(20000, "MYR", "USD");
        System.out.println("20000 MYR = " + usd + " USD (live rate)");
        System.out.println();

        // The need-based rule now using the real, live converter.
        Applicant applicant = new Applicant("A001", "Sara", 3.8, 20000, "MYR");
        NeedBasedEligibilityStrategy needRule =
                new NeedBasedEligibilityStrategy(6000, "USD", converter);
        Scholarship needScholarship =
                new Scholarship("S002", "Need-Based Scholarship", needRule);

        EligibilityResult result = needScholarship.checkEligibility(applicant);
        System.out.println(needScholarship.getName() + " -> eligible: "
                + result.isEligible() + " | " + result.getReason());
    }
}