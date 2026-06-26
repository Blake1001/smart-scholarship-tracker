package org.example.observer;

import org.example.model.Application;
import org.example.model.ApplicationStatus;

import java.util.ArrayList;
import java.util.List;
/**
 * Keeps a record of every status change for auditing. Instead of printing,
 * it stores each change as a line of text that can be reviewed later.
 */
public class AuditLogObserver implements StatusObserver {

    private final List<String> log = new ArrayList<>();

    @Override
    public void onStatusChanged(Application application,
                                ApplicationStatus oldStatus,
                                ApplicationStatus newStatus) {
        log.add("Application " + application.getId() + ": "
                + oldStatus + " -> " + newStatus);
    }

    /** @return all recorded status changes */
    public List<String> getLog() {
        return log;
    }
}