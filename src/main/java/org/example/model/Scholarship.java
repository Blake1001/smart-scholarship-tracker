package org.example.model;

import org.example.strategy.EligibilityStrategy;

/**
 * A scholarship that applicants can apply for. Each scholarship holds its
 * own eligibility rule (Strategy pattern), so different scholarship types
 * can use different rules without changing this class.
 */
public class Scholarship {

    private final String id;
    private final String name;
    private final EligibilityStrategy eligibilityStrategy;

    /**
     * Creates a scholarship.
     *
     * @param id                  unique identifier
     * @param name                display name
     * @param eligibilityStrategy the rule used to judge applicants
     */
    public Scholarship(String id, String name, EligibilityStrategy eligibilityStrategy) {
        this.id = id;
        this.name = name;
        this.eligibilityStrategy = eligibilityStrategy;
    }

    /**
     * Checks an applicant against this scholarship's rule.
     *
     * @param applicant the applicant to evaluate
     * @return the eligibility decision and its reason
     */
    public EligibilityResult checkEligibility(Applicant applicant) {
        return eligibilityStrategy.evaluate(applicant);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}