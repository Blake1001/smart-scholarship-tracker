package org.example.observer;

import org.example.model.Applicant;
import org.example.model.Application;
import org.example.model.ApplicationStatus;
import org.example.model.EligibilityResult;
import org.example.model.Scholarship;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ObserverTest {

    private Application sampleApplication() {
        Applicant applicant = new Applicant("A1", "Sara", 3.8, 20000, "MYR");
        Scholarship scholarship = new Scholarship("S1", "Merit",
                a -> new EligibilityResult(true, "ok"));
        return new Application("APP1", applicant, scholarship);
    }

    @Test
    void auditLog_afterStatusChange_recordsOneEntry() {
        Application app = sampleApplication();
        AuditLogObserver audit = new AuditLogObserver();
        app.addObserver(audit);

        app.setStatus(ApplicationStatus.ELIGIBLE);

        assertEquals(1, audit.getLog().size());
        assertTrue(audit.getLog().get(0).contains("PENDING -> ELIGIBLE"));
    }

    @Test
    void auditLog_afterMultipleChanges_recordsEachOne() {
        Application app = sampleApplication();
        AuditLogObserver audit = new AuditLogObserver();
        app.addObserver(audit);

        app.setStatus(ApplicationStatus.WAITLISTED);
        app.setStatus(ApplicationStatus.ELIGIBLE);

        assertEquals(2, audit.getLog().size());
    }

    @Test
    void applicantNotifier_whenNotified_doesNotThrow() {
        Application app = sampleApplication();
        app.addObserver(new ApplicantNotifier());
        assertDoesNotThrow(() -> app.setStatus(ApplicationStatus.ELIGIBLE));
    }

    @Test
    void officerNotifier_whenNotified_doesNotThrow() {
        Application app = sampleApplication();
        app.addObserver(new OfficerNotifier());
        assertDoesNotThrow(() -> app.setStatus(ApplicationStatus.INELIGIBLE));
    }
}