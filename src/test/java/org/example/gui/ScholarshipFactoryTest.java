package org.example.gui;

import org.example.api.CurrencyConverter;
import org.example.model.Applicant;
import org.example.model.EligibilityResult;
import org.example.model.Scholarship;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScholarshipFactoryTest {

    private final CurrencyConverter fakeConverter =
            (amount, from, to) -> from.equals(to) ? amount : amount * 0.25;
    private final ScholarshipFactory factory = new ScholarshipFactory(fakeConverter);

    @Test
    void create_meritScholarship_checksGpaOnly() {
        Scholarship s = factory.create("Merit Scholarship");
        EligibilityResult r = s.checkEligibility(new Applicant("A1", "X", 3.8, 9999999, "MYR"));
        assertTrue(r.isEligible());
    }

    @Test
    void create_needBasedScholarship_checksIncome() {
        Scholarship s = factory.create("Need-Based Scholarship");
        EligibilityResult r = s.checkEligibility(new Applicant("A1", "X", 1.0, 100000, "MYR"));
        assertFalse(r.isEligible());
    }

    @Test
    void create_comboScholarship_requiresBoth() {
        Scholarship s = factory.create("Merit + Need Scholarship");
        EligibilityResult r = s.checkEligibility(new Applicant("A1", "X", 3.8, 1000, "MYR"));
        assertTrue(r.isEligible());
    }

    @Test
    void create_unknownName_defaultsToMerit() {
        Scholarship s = factory.create("Anything Else");
        EligibilityResult r = s.checkEligibility(new Applicant("A1", "X", 3.8, 0, "MYR"));
        assertTrue(r.isEligible());
    }
}