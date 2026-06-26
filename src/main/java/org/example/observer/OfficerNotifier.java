package org.example.observer;

import org.example.model.ApplicationStatus;
import org.example.model.Application;

/**
 * Notifies the scholarship officer when an application's status changes.
 */
public class OfficerNotifier implements StatusObserver {

    @Override
    public void onStatusChanged(Application application,
                                ApplicationStatus oldStatus,
                                ApplicationStatus newStatus) {
        System.out.println("[To officer] Application "
                + application.getId() + " changed from "
                + oldStatus + " to " + newStatus);
    }
}