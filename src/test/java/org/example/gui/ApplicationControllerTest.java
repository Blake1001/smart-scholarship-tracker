package org.example.gui;

import org.example.api.CurrencyConversionException;
import org.example.api.CurrencyConverter;
import org.example.model.Application;
import org.example.model.ApplicationStatus;
import org.example.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ApplicationControllerTest {

    private final CurrencyConverter fakeConverter =
            (amount, from, to) -> from.equals(to) ? amount : amount * 0.25;

    private ApplicationController controller;

    @BeforeEach
    void setUp() {
        ApplicationService service = new ApplicationService();
        ScholarshipFactory factory = new ScholarshipFactory(fakeConverter);
        controller = new ApplicationController(service, factory, new ResultPanel());
    }

    @Test
    void submit_validEligibleApplicant_returnsApplication() {
        Application app = controller.submit("Sara", "3.8", "1000", "MYR", "Merit Scholarship");
        assertNotNull(app);
        assertEquals(ApplicationStatus.ELIGIBLE, app.getStatus());
    }

    @Test
    void submit_blankName_returnsNull() {
        assertNull(controller.submit("", "3.8", "1000", "MYR", "Merit Scholarship"));
    }

    @Test
    void submit_nonNumericGpa_returnsNull() {
        assertNull(controller.submit("Sara", "abc", "1000", "MYR", "Merit Scholarship"));
    }

    @Test
    void submit_gpaOutOfRange_returnsNull() {
        assertNull(controller.submit("Sara", "9.0", "1000", "MYR", "Merit Scholarship"));
    }

    @Test
    void submit_negativeIncome_returnsNull() {
        assertNull(controller.submit("Sara", "3.8", "-50", "MYR", "Merit Scholarship"));
    }

    @Test
    void submit_whenCurrencyServiceFails_returnsNull() {
        CurrencyConverter throwing = (amount, from, to) -> {
            throw new CurrencyConversionException("down", null);
        };
        ApplicationController c = new ApplicationController(
                new ApplicationService(),
                new ScholarshipFactory(throwing),
                new ResultPanel());
        assertNull(c.submit("Sara", "3.8", "1000", "MYR", "Need-Based Scholarship"));
    }
}