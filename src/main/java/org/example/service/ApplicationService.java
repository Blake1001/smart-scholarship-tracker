package org.example.service;

import org.example.model.Applicant;
import org.example.model.Application;
import org.example.model.ApplicationStatus;
import org.example.model.EligibilityResult;
import org.example.model.Scholarship;
import org.example.observer.StatusObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Coordinates the scholarship workflow: it creates an application, attaches
 * the observers that should be notified, checks the applicant against the
 * scholarship's rule, and updates the status (which triggers the
 * notifications). The GUI talks to this class instead of wiring the steps
 * itself.
 */
public class ApplicationService {

    private final List<StatusObserver> observers = new ArrayList<>();
    private int nextApplicationNumber = 1;

    /**
     * Registers an observer that will be attached to every new application.
     *
     * @param observer the observer to notify on status changes
     */
    public void registerObserver(StatusObserver observer) {
        observers.add(observer);
    }

    /**
     * Processes one application from start to finish: creates it, attaches the
     * registered observers, evaluates eligibility, and sets the resulting
     * status.
     *
     * @param applicant   the student applying
     * @param scholarship the scholarship applied for
     * @return the processed application, now carrying its final status
     */
    public Application processApplication(Applicant applicant, Scholarship scholarship) {
        Application application = new Application(generateId(), applicant, scholarship);

        for (StatusObserver observer : observers) {
            application.addObserver(observer);
        }

        EligibilityResult result = scholarship.checkEligibility(applicant);
        ApplicationStatus decision = result.isEligible()
                ? ApplicationStatus.ELIGIBLE
                : ApplicationStatus.INELIGIBLE;

        application.setStatus(decision);
        return application;
    }

    private String generateId() {
        String id = "APP" + nextApplicationNumber;
        nextApplicationNumber++;
        return id;
    }
}