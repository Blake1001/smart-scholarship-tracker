package org.example.observer;

import org.example.model.ApplicationStatus;
import org.example.model.Application;

/**
 * Notifies the applicant when their application's status changes.
 */
public class ApplicantNotifier implements StatusObserver {

    @Override
    public void onStatusChanged(Application application,
                                ApplicationStatus oldStatus,
                                ApplicationStatus newStatus) {
        System.out.println("[To applicant] Dear "
                + application.getApplicant().getName()
                + ", your application status is now: " + newStatus);
    }
}