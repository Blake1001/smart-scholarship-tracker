package org.example.model;

import org.example.strategy.EligibilityStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScholarshipTest {

    @Test
    void checkEligibility_delegatesToStrategy_returnsStrategyResult() {
        EligibilityStrategy stub = applicant -> new EligibilityResult(true, "from strategy");
        Scholarship scholarship = new Scholarship("S1", "Test", stub);

        EligibilityResult result =
                scholarship.checkEligibility(new Applicant("A1", "Sara", 3.0, 0, "USD"));

        assertTrue(result.isEligible());
        assertEquals("from strategy", result.getReason());
    }

    @Test
    void getters_returnConstructorValues() {
        Scholarship scholarship =
                new Scholarship("S9", "Need", a -> new EligibilityResult(false, "no"));
        assertEquals("S9", scholarship.getId());
        assertEquals("Need", scholarship.getName());
    }
}