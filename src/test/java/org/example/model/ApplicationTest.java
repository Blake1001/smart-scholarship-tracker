package org.example.model;

import org.example.observer.StatusObserver;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ApplicationTest {

    private Application newApplication() {
        Applicant applicant = new Applicant("A1", "Sara", 3.8, 20000, "MYR");
        Scholarship scholarship = new Scholarship("S1", "Merit",
                a -> new EligibilityResult(true, "ok"));
        return new Application("APP1", applicant, scholarship);
    }

    @Test
    void newApplication_whenCreated_startsAsPending() {
        assertEquals(ApplicationStatus.PENDING, newApplication().getStatus());
    }

    @Test
    void setStatus_whenCalled_updatesStatus() {
        Application app = newApplication();
        app.setStatus(ApplicationStatus.ELIGIBLE);
        assertEquals(ApplicationStatus.ELIGIBLE, app.getStatus());
    }

    @Test
    void setStatus_whenCalled_notifiesObserver() {
        Application app = newApplication();
        List<String> received = new ArrayList<>();
        StatusObserver recorder =
                (application, oldStatus, newStatus) -> received.add(oldStatus + "->" + newStatus);
        app.addObserver(recorder);

        app.setStatus(ApplicationStatus.ELIGIBLE);

        assertEquals(1, received.size());
        assertEquals("PENDING->ELIGIBLE", received.get(0));
    }

    @Test
    void setStatus_withMultipleObservers_notifiesAll() {
        Application app = newApplication();
        List<String> log = new ArrayList<>();
        app.addObserver((a, o, n) -> log.add("one"));
        app.addObserver((a, o, n) -> log.add("two"));

        app.setStatus(ApplicationStatus.WAITLISTED);

        assertEquals(2, log.size());
    }

    @Test
    void getters_returnConstructorValues() {
        Application app = newApplication();
        assertAll("application getters",
                () -> assertEquals("APP1", app.getId()),
                () -> assertEquals("Sara", app.getApplicant().getName()),
                () -> assertEquals("Merit", app.getScholarship().getName())
        );
    }
}