package org.example;

import org.example.model.ApplicationStatus;
import org.example.model.Applicant;
import org.example.model.Application;
import org.example.model.EligibilityResult;
import org.example.model.Scholarship;
import org.example.observer.ApplicantNotifier;
import org.example.observer.AuditLogObserver;
import org.example.observer.OfficerNotifier;
import org.example.strategy.MeritEligibilityStrategy;

/**
 * Temporary entry point used to try the Observer pattern by hand.
 * It will be replaced by the graphical interface in a later phase.
 */
public class Main {

    public static void main(String[] args) {
        Applicant applicant = new Applicant("A001", "Sara", 3.8, 20000, "MYR");
        Scholarship meritScholarship =
                new Scholarship("S001", "Merit Scholarship", new MeritEligibilityStrategy(3.5));

        Application application = new Application("APP100", applicant, meritScholarship);

        // Register the three observers.
        application.addObserver(new ApplicantNotifier());
        application.addObserver(new OfficerNotifier());
        AuditLogObserver auditLog = new AuditLogObserver();
        application.addObserver(auditLog);

        // Decide the outcome, then change the status (this triggers the notifications).
        EligibilityResult result = meritScholarship.checkEligibility(applicant);
        ApplicationStatus decision =
                result.isEligible() ? ApplicationStatus.ELIGIBLE : ApplicationStatus.INELIGIBLE;

        System.out.println("Changing status...");
        application.setStatus(decision);

        System.out.println();
        System.out.println("Audit log contents:");
        for (String entry : auditLog.getLog()) {
            System.out.println(" - " + entry);
        }
    }
}