package org.example.strategy;

import org.example.api.CurrencyConverter;
import org.example.model.Applicant;
import org.example.model.EligibilityResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the eligibility strategies (the Strategy pattern).
 * A simple fake currency converter is used so the tests run quickly and
 * without any network access.
 */
class EligibilityStrategyTest {

    // A fake converter that always treats 1 unit of any currency as 0.25 USD.
    // This keeps the income tests predictable and offline.
    private final CurrencyConverter fakeConverter =
            (amount, from, to) -> from.equals(to) ? amount : amount * 0.25;

    private Applicant applicantWith(double gpa, double income) {
        return new Applicant("A1", "Test", gpa, income, "MYR");
    }

    // ---------- Merit strategy ----------

    @Test
    void evaluate_gpaAboveMinimum_returnsEligible() {
        MeritEligibilityStrategy rule = new MeritEligibilityStrategy(3.5);
        EligibilityResult result = rule.evaluate(applicantWith(3.8, 0));
        assertTrue(result.isEligible());
    }

    @Test
    void evaluate_gpaBelowMinimum_returnsIneligible() {
        MeritEligibilityStrategy rule = new MeritEligibilityStrategy(3.5);
        EligibilityResult result = rule.evaluate(applicantWith(3.0, 0));
        assertFalse(result.isEligible());
    }

    @Test
    void evaluate_gpaExactlyAtMinimum_returnsEligible() {
        MeritEligibilityStrategy rule = new MeritEligibilityStrategy(3.5);
        EligibilityResult result = rule.evaluate(applicantWith(3.5, 0));
        assertTrue(result.isEligible());
    }

    @ParameterizedTest
    @CsvSource({
            "4.0, true",
            "3.5, true",
            "3.49, false",
            "2.0, false",
            "0.0, false"
    })
    void evaluate_variousGpaValues_returnsExpected(double gpa, boolean expected) {
        MeritEligibilityStrategy rule = new MeritEligibilityStrategy(3.5);
        EligibilityResult result = rule.evaluate(applicantWith(gpa, 0));
        assertEquals(expected, result.isEligible());
    }

    // ---------- Need-based strategy ----------

    @Test
    void evaluate_incomeWithinLimit_returnsEligible() {
        NeedBasedEligibilityStrategy rule =
                new NeedBasedEligibilityStrategy(6000, "USD", fakeConverter);
        // 20000 MYR * 0.25 = 5000 USD, which is <= 6000
        EligibilityResult result = rule.evaluate(applicantWith(0, 20000));
        assertTrue(result.isEligible());
    }

    @Test
    void evaluate_incomeAboveLimit_returnsIneligible() {
        NeedBasedEligibilityStrategy rule =
                new NeedBasedEligibilityStrategy(6000, "USD", fakeConverter);
        // 40000 MYR * 0.25 = 10000 USD, which is > 6000
        EligibilityResult result = rule.evaluate(applicantWith(0, 40000));
        assertFalse(result.isEligible());
    }

    @ParameterizedTest
    @CsvSource({
            "20000, true",
            "24000, true",
            "24001, false",
            "100000, false"
    })
    void evaluate_variousIncomes_returnsExpected(double income, boolean expected) {
        NeedBasedEligibilityStrategy rule =
                new NeedBasedEligibilityStrategy(6000, "USD", fakeConverter);
        EligibilityResult result = rule.evaluate(applicantWith(0, income));
        assertEquals(expected, result.isEligible());
    }

    // ---------- Combined strategy ----------

    @Test
    void evaluate_meetsBothMeritAndNeed_returnsEligible() {
        MeritEligibilityStrategy merit = new MeritEligibilityStrategy(3.5);
        NeedBasedEligibilityStrategy need =
                new NeedBasedEligibilityStrategy(6000, "USD", fakeConverter);
        MeritAndNeedEligibilityStrategy combined =
                new MeritAndNeedEligibilityStrategy(merit, need);

        EligibilityResult result = combined.evaluate(applicantWith(3.8, 20000));
        assertTrue(result.isEligible());
    }

    @Test
    void evaluate_meetsNeedButNotMerit_returnsIneligible() {
        MeritEligibilityStrategy merit = new MeritEligibilityStrategy(3.5);
        NeedBasedEligibilityStrategy need =
                new NeedBasedEligibilityStrategy(6000, "USD", fakeConverter);
        MeritAndNeedEligibilityStrategy combined =
                new MeritAndNeedEligibilityStrategy(merit, need);

        // GPA 3.0 fails merit, even though income is fine
        EligibilityResult result = combined.evaluate(applicantWith(3.0, 20000));
        assertFalse(result.isEligible());
    }

    @Test
    void evaluate_resultCarriesDecisionAndReason_togetherAreConsistent() {
        MeritEligibilityStrategy rule = new MeritEligibilityStrategy(3.5);
        EligibilityResult result = rule.evaluate(applicantWith(3.8, 0));

        // assertAll checks several things and reports all failures at once.
        assertAll("eligible result",
                () -> assertTrue(result.isEligible()),
                () -> assertTrue(result.getReason().contains("3.5")),
                () -> assertFalse(result.getReason().isBlank())
        );
    }

    // ---------- Exception tests ----------

    @Test
    void evaluate_nullApplicant_throwsException() {
        MeritEligibilityStrategy rule = new MeritEligibilityStrategy(3.5);
        assertThrows(NullPointerException.class, () -> rule.evaluate(null));
    }

    @Test
    void evaluate_nullApplicantInNeedRule_throwsException() {
        NeedBasedEligibilityStrategy rule =
                new NeedBasedEligibilityStrategy(6000, "USD", fakeConverter);
        assertThrows(NullPointerException.class, () -> rule.evaluate(null));
    }
}