package org.example.model;

import org.example.observer.StatusObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * A student's application for one scholarship. It tracks the current status
 * and notifies all registered observers whenever the status changes
 * (the "subject" in the Observer pattern).
 */
public class Application {

    private final String id;
    private final Applicant applicant;
    private final Scholarship scholarship;
    private ApplicationStatus status;
    private final List<StatusObserver> observers = new ArrayList<>();

    /**
     * Creates a new application with the status PENDING.
     *
     * @param id          unique identifier
     * @param applicant   the student applying
     * @param scholarship the scholarship applied for
     */
    public Application(String id, Applicant applicant, Scholarship scholarship) {
        this.id = id;
        this.applicant = applicant;
        this.scholarship = scholarship;
        this.status = ApplicationStatus.PENDING;
    }

    /**
     * Registers an observer to be notified of status changes.
     *
     * @param observer the observer to add
     */
    public void addObserver(StatusObserver observer) {
        observers.add(observer);
    }

    /**
     * Changes the status and notifies all observers of the change.
     *
     * @param newStatus the new status
     */
    public void setStatus(ApplicationStatus newStatus) {
        ApplicationStatus oldStatus = this.status;
        this.status = newStatus;
        notifyObservers(oldStatus, newStatus);
    }

    private void notifyObservers(ApplicationStatus oldStatus, ApplicationStatus newStatus) {
        for (StatusObserver observer : observers) {
            observer.onStatusChanged(this, oldStatus, newStatus);
        }
    }

    public String getId() {
        return id;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public Scholarship getScholarship() {
        return scholarship;
    }

    public ApplicationStatus getStatus() {
        return status;
    }
}