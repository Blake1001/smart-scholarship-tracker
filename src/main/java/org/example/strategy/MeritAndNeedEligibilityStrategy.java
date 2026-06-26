package org.example.strategy;

import org.example.model.Applicant;
import org.example.model.EligibilityResult;

/**
 * Eligibility rule for scholarships that require both academic merit and
 * financial need. The applicant qualifies only when both the merit rule
 * and the need-based rule pass. This class reuses the two existing rules
 * instead of repeating their logic (composition).
 */
public class MeritAndNeedEligibilityStrategy implements EligibilityStrategy {

    private final MeritEligibilityStrategy meritRule;
    private final NeedBasedEligibilityStrategy needRule;

    /**
     * Creates a combined rule from an existing merit rule and need rule.
     *
     * @param meritRule the GPA-based rule
     * @param needRule  the income-based rule
     */
    public MeritAndNeedEligibilityStrategy(MeritEligibilityStrategy meritRule,
                                           NeedBasedEligibilityStrategy needRule) {
        this.meritRule = meritRule;
        this.needRule = needRule;
    }

    @Override
    public EligibilityResult evaluate(Applicant applicant) {
        EligibilityResult meritResult = meritRule.evaluate(applicant);
        if (!meritResult.isEligible()) {
            return meritResult;
        }

        EligibilityResult needResult = needRule.evaluate(applicant);
        if (!needResult.isEligible()) {
            return needResult;
        }

        return new EligibilityResult(true,
                "Meets both merit and need requirements");
    }
}