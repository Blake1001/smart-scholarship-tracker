package org.example.strategy;

import org.example.model.Applicant;
import org.example.model.EligibilityResult;

/**
 * A rule that decides whether an applicant qualifies for a scholarship.
 * Each scholarship type provides its own implementation (Strategy pattern).
 */
public interface EligibilityStrategy {

    /**
     * Checks the applicant against this rule.
     *
     * @param applicant the applicant to evaluate
     * @return the eligibility decision and its reason
     */
    EligibilityResult evaluate(Applicant applicant);
}