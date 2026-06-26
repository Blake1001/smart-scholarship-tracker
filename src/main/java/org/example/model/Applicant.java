package org.example.model;

/**
 * A student who applies for a scholarship.
 * Holds the academic and financial details used to check eligibility.
 */
public class Applicant {

    private final String id;
    private final String name;
    private final double gpa;
    private final double annualIncome;
    private final String incomeCurrency;

    /**
     * Creates an applicant.
     *
     * @param id             unique identifier
     * @param name           full name
     * @param gpa            grade point average (for example, 0.0 to 4.0)
     * @param annualIncome   yearly income in the applicant's own currency
     * @param incomeCurrency three-letter currency code, such as "MYR"
     */
    public Applicant(String id, String name, double gpa,
                     double annualIncome, String incomeCurrency) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
        this.annualIncome = annualIncome;
        this.incomeCurrency = incomeCurrency;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getGpa() {
        return gpa;
    }

    public double getAnnualIncome() {
        return annualIncome;
    }

    public String getIncomeCurrency() {
        return incomeCurrency;
    }
}