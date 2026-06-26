package org.example.gui;

import org.example.api.CurrencyConversionException;
import org.example.model.Applicant;
import org.example.model.Application;
import org.example.model.EligibilityResult;
import org.example.model.Scholarship;
import org.example.service.ApplicationService;

/**
 * The Controller in the MVC structure. It takes the raw input from the form,
 * validates it, builds the model objects, asks the service to process the
 * application, and reports the result (or an error) back to the views.
 */
public class ApplicationController {

    private static final double MIN_GPA = 0.0;
    private static final double MAX_GPA = 4.0;

    private final ApplicationService applicationService;
    private final ResultPanel resultPanel;
    private final ScholarshipFactory scholarshipFactory;

    /**
     * Creates the controller.
     *
     * @param applicationService the service that processes applications
     * @param scholarshipFactory builds a Scholarship from the chosen name
     * @param resultPanel        the screen that displays the outcome
     */
    public ApplicationController(ApplicationService applicationService,
                                 ScholarshipFactory scholarshipFactory,
                                 ResultPanel resultPanel) {
        this.applicationService = applicationService;
        this.scholarshipFactory = scholarshipFactory;
        this.resultPanel = resultPanel;
    }

    /**
     * Validates the input, processes the application, and updates the result
     * screen. Any invalid input or service failure is shown as a friendly
     * message instead of a crash.
     *
     * @param name           the applicant's name
     * @param gpaText        the GPA as typed by the user
     * @param incomeText     the income as typed by the user
     * @param currency       the income currency
     * @param scholarshipName the chosen scholarship name
     * @return the processed application, or null if the input was invalid
     */
    public Application submit(String name, String gpaText, String incomeText,
                              String currency, String scholarshipName) {
        if (name == null || name.isBlank()) {
            resultPanel.showError("Please enter the applicant's name.");
            return null;
        }

        double gpa;
        double income;
        try {
            gpa = Double.parseDouble(gpaText.trim());
            income = Double.parseDouble(incomeText.trim());
        } catch (NumberFormatException e) {
            resultPanel.showError("GPA and income must be valid numbers.");
            return null;
        }

        if (gpa < MIN_GPA || gpa > MAX_GPA) {
            resultPanel.showError("GPA must be between " + MIN_GPA + " and " + MAX_GPA + ".");
            return null;
        }
        if (income < 0) {
            resultPanel.showError("Income cannot be negative.");
            return null;
        }

        Applicant applicant = new Applicant("A" + System.currentTimeMillis(),
                name.trim(), gpa, income, currency);
        Scholarship scholarship = scholarshipFactory.create(scholarshipName);

        try {
            Application application =
                    applicationService.processApplication(applicant, scholarship);
            EligibilityResult result = scholarship.checkEligibility(applicant);
            resultPanel.showResult(application.getStatus(), result.getReason());
            return application;
        } catch (CurrencyConversionException e) {
            resultPanel.showError("Currency service unavailable. Please try again later.");
            return null;
        }
    }
}