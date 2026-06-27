package org.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModelGettersTest {

    @Test
    void applicant_getters_returnConstructorValues() {
        Applicant a = new Applicant("A1", "Sara", 3.8, 20000, "MYR");
        assertAll("applicant",
                () -> assertEquals("A1", a.getId()),
                () -> assertEquals("Sara", a.getName()),
                () -> assertEquals(3.8, a.getGpa()),
                () -> assertEquals(20000.0, a.getAnnualIncome()),
                () -> assertEquals("MYR", a.getIncomeCurrency())
        );
    }

    @Test
    void eligibilityResult_getters_returnConstructorValues() {
        EligibilityResult r = new EligibilityResult(true, "reason text");
        assertTrue(r.isEligible());
        assertEquals("reason text", r.getReason());
    }

    @Test
    void applicationStatus_hasFourValues() {
        assertEquals(ApplicationStatus.ELIGIBLE, ApplicationStatus.valueOf("ELIGIBLE"));
        assertEquals(4, ApplicationStatus.values().length);
    }
}