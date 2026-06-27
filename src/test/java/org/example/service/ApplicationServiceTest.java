package org.example.service;

import org.example.model.Applicant;
import org.example.model.Application;
import org.example.model.ApplicationStatus;
import org.example.model.Scholarship;
import org.example.observer.AuditLogObserver;
import org.example.strategy.MeritEligibilityStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ApplicationServiceTest {

    private Scholarship meritScholarship() {
        return new Scholarship("S1", "Merit", new MeritEligibilityStrategy(3.5));
    }

    private Applicant applicant(double gpa) {
        return new Applicant("A1", "Test", gpa, 0, "USD");
    }

    @Test
    void processApplication_eligibleApplicant_setsStatusEligible() {
        ApplicationService service = new ApplicationService();
        Application app = service.processApplication(applicant(3.8), meritScholarship());
        assertEquals(ApplicationStatus.ELIGIBLE, app.getStatus());
    }

    @Test
    void processApplication_ineligibleApplicant_setsStatusIneligible() {
        ApplicationService service = new ApplicationService();
        Application app = service.processApplication(applicant(2.0), meritScholarship());
        assertEquals(ApplicationStatus.INELIGIBLE, app.getStatus());
    }

    @Test
    void processApplication_calledTwice_generatesDifferentIds() {
        ApplicationService service = new ApplicationService();
        Application first = service.processApplication(applicant(3.8), meritScholarship());
        Application second = service.processApplication(applicant(3.8), meritScholarship());
        assertNotEquals(first.getId(), second.getId());
    }

    @Test
    void processApplication_withRegisteredObserver_notifiesIt() {
        ApplicationService service = new ApplicationService();
        AuditLogObserver audit = new AuditLogObserver();
        service.registerObserver(audit);

        service.processApplication(applicant(3.8), meritScholarship());

        assertEquals(1, audit.getLog().size());
    }

    @ParameterizedTest
    @CsvSource({
            "4.0, ELIGIBLE",
            "3.5, ELIGIBLE",
            "3.0, INELIGIBLE",
            "1.0, INELIGIBLE"
    })
    void processApplication_variousGpas_setsExpectedStatus(double gpa, ApplicationStatus expected) {
        ApplicationService service = new ApplicationService();
        Application app = service.processApplication(applicant(gpa), meritScholarship());
        assertEquals(expected, app.getStatus());
    }
}