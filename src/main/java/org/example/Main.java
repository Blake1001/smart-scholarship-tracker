package org.example;

import org.example.api.CurrencyConverter;
import org.example.model.Applicant;
import org.example.model.EligibilityResult;
import org.example.model.Scholarship;
import org.example.strategy.MeritAndNeedEligibilityStrategy;
import org.example.strategy.MeritEligibilityStrategy;
import org.example.strategy.NeedBasedEligibilityStrategy;

/**
 * Temporary entry point used to try out the core classes by hand.
 * It will be replaced by the graphical interface in a later phase.
 */
public class Main {

    public static void main(String[] args) {
        Applicant applicant = new Applicant("A001", "Sara", 3.8, 20000, "MYR");

        // Temporary fake converter: pretends 1 MYR = 0.21 USD.
        // The real one that calls the website is added in the next batch.
        CurrencyConverter fakeConverter = (amount, from, to) -> amount * 0.21;

        // Rule 1: merit only
        Scholarship meritScholarship =
                new Scholarship("S001", "Merit Scholarship", new MeritEligibilityStrategy(3.5));

        // Rule 2: need based (max income 6000 USD)
        NeedBasedEligibilityStrategy needRule =
                new NeedBasedEligibilityStrategy(6000, "USD", fakeConverter);
        Scholarship needScholarship =
                new Scholarship("S002", "Need-Based Scholarship", needRule);

        // Rule 3: both merit and need
        Scholarship comboScholarship = new Scholarship("S003", "Merit + Need Scholarship",
                new MeritAndNeedEligibilityStrategy(new MeritEligibilityStrategy(3.5), needRule));

        printResult(meritScholarship, applicant);
        printResult(needScholarship, applicant);
        printResult(comboScholarship, applicant);
    }

    private static void printResult(Scholarship scholarship, Applicant applicant) {
        EligibilityResult result = scholarship.checkEligibility(applicant);
        System.out.println(scholarship.getName() + " -> eligible: "
                + result.isEligible() + " | " + result.getReason());
        System.out.println();
    }
}