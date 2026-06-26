package org.example.strategy;

import org.example.model.Applicant;
import org.example.model.EligibilityResult;

/**
 * Eligibility rule for merit scholarships: the applicant qualifies
 * when their GPA is at least the required minimum.
 */
public class MeritEligibilityStrategy implements EligibilityStrategy {

    private final double minimumGpa;

    /**
     * Creates a merit rule.
     *
     * @param minimumGpa the lowest GPA that still qualifies
     */
    public MeritEligibilityStrategy(double minimumGpa) {
        this.minimumGpa = minimumGpa;
    }

    @Override
    public EligibilityResult evaluate(Applicant applicant) {
        if (applicant.getGpa() >= minimumGpa) {
            return new EligibilityResult(true,
                    "GPA " + applicant.getGpa() + " meets the minimum of " + minimumGpa);
        }
        return new EligibilityResult(false,
                "GPA " + applicant.getGpa() + " is below the minimum of " + minimumGpa);
    }
}