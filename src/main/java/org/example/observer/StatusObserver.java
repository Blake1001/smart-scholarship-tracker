package org.example.observer;

import org.example.model.Application;
import org.example.model.ApplicationStatus;

/**
 * Something that wants to be told when an application's status changes.
 * Each observer decides for itself what to do with the notification
 * (Observer pattern).
 */
public interface StatusObserver {

    /**
     * Called automatically when an application's status changes.
     *
     * @param application the application whose status changed
     * @param oldStatus   the status before the change
     * @param newStatus   the status after the change
     */
    void onStatusChanged(Application application,
                         ApplicationStatus oldStatus,
                         ApplicationStatus newStatus);
}