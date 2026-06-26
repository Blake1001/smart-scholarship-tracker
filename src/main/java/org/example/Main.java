package org.example;

import org.example.model.Applicant;
import org.example.model.EligibilityResult;
import org.example.model.Scholarship;
import org.example.strategy.MeritEligibilityStrategy;

/**
 * Temporary entry point used to try out the core classes by hand.
 * It will be replaced by the graphical interface in a later phase.
 */
public class Main {

    public static void main(String[] args) {
        Applicant applicant = new Applicant("A001", "Sara", 3.8, 20000, "MYR");

        Scholarship meritScholarship =
                new Scholarship("S001", "Merit Scholarship", new MeritEligibilityStrategy(3.5));

        EligibilityResult result = meritScholarship.checkEligibility(applicant);

        System.out.println("Applicant: " + applicant.getName());
        System.out.println("Scholarship: " + meritScholarship.getName());
        System.out.println("Eligible: " + result.isEligible());
        System.out.println("Reason: " + result.getReason());
    }
}