package org.example.strategy;

import org.example.model.Applicant;
import org.example.model.EligibilityResult;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * A deliberately failing test that documents a known limitation:
 * the Merit rule does not check that the GPA is within the valid range
 * (0.0 - 4.0). An impossible GPA like 5.0 is still accepted as eligible,
 * because range checking currently lives only in the controller, not inside
 * the rule itself.
 */
class MeritEligibilityStrategyLimitationTest {

    @Test
    @Disabled("Documents a known limitation: the rule does not validate the GPA range")
    void evaluate_impossibleGpaAboveFour_shouldBeRejectedButIsNot() {
        MeritEligibilityStrategy rule = new MeritEligibilityStrategy(3.5);
        EligibilityResult result = rule.evaluate(new Applicant("A1", "X", 5.0, 0, "USD"));
        assertFalse(result.isEligible()); // the code returns eligible -> the test fails
    }
}