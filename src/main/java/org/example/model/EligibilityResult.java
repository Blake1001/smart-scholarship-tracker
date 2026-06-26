package org.example.model;

/**
 * The outcome of checking an applicant against a scholarship's rule.
 * Holds whether the applicant is eligible and a short, readable reason.
 */
public class EligibilityResult {

    private final boolean eligible;
    private final String reason;

    /**
     * Creates an eligibility result.
     *
     * @param eligible whether the applicant meets the rule
     * @param reason   a short explanation of the decision
     */
    public EligibilityResult(boolean eligible, String reason) {
        this.eligible = eligible;
        this.reason = reason;
    }

    /** @return true if the applicant meets the rule */
    public boolean isEligible() {
        return eligible;
    }

    /** @return a short explanation of the decision */
    public String getReason() {
        return reason;
    }
}