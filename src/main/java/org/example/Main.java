package org.example;

import org.example.api.CurrencyConverter;
import org.example.api.FrankfurterCurrencyConverter;
import org.example.model.Applicant;
import org.example.model.Application;
import org.example.model.Scholarship;
import org.example.observer.ApplicantNotifier;
import org.example.observer.OfficerNotifier;
import org.example.service.ApplicationService;
import org.example.strategy.MeritAndNeedEligibilityStrategy;
import org.example.strategy.MeritEligibilityStrategy;
import org.example.strategy.NeedBasedEligibilityStrategy;

/**
 * Temporary entry point used to try the service layer by hand.
 * It will be replaced by the graphical interface in a later phase.
 */
public class Main {

    public static void main(String[] args) {
        CurrencyConverter converter = new FrankfurterCurrencyConverter();

        // Set up the service once, with the observers it should always notify.
        ApplicationService service = new ApplicationService();
        service.registerObserver(new ApplicantNotifier());
        service.registerObserver(new OfficerNotifier());

        // Build a "merit + need" scholarship.
        MeritEligibilityStrategy meritRule = new MeritEligibilityStrategy(3.5);
        NeedBasedEligibilityStrategy needRule =
                new NeedBasedEligibilityStrategy(6000, "USD", converter);
        Scholarship scholarship = new Scholarship("S003", "Merit + Need Scholarship",
                new MeritAndNeedEligibilityStrategy(meritRule, needRule));

        // Process two applicants — one line each.
        Applicant sara = new Applicant("A001", "Sara", 3.8, 20000, "MYR");
        Applicant omar = new Applicant("A002", "Omar", 3.1, 15000, "MYR");

        System.out.println("--- Processing Sara ---");
        Application saraApp = service.processApplication(sara, scholarship);
        System.out.println("Final status: " + saraApp.getStatus());

        System.out.println();
        System.out.println("--- Processing Omar ---");
        Application omarApp = service.processApplication(omar, scholarship);
        System.out.println("Final status: " + omarApp.getStatus());
    }
}