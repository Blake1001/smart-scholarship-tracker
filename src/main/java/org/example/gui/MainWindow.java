package org.example.gui;

import org.example.api.CurrencyConverter;
import org.example.api.FrankfurterCurrencyConverter;
import org.example.observer.ApplicantNotifier;
import org.example.observer.OfficerNotifier;
import org.example.service.ApplicationService;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 * The main application window. It builds the model objects, the views, and the
 * controller, then wires the form's button to the controller. This is the
 * top-level View that assembles the MVC pieces.
 */
public class MainWindow extends JFrame {

    private static final int WINDOW_WIDTH = 640;
    private static final int WINDOW_HEIGHT = 480;

    /** Builds and wires the whole application window. */
    public MainWindow() {
        setTitle("Smart Scholarship Tracker");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Model side
        CurrencyConverter converter = new FrankfurterCurrencyConverter();
        ApplicationService service = new ApplicationService();
        service.registerObserver(new ApplicantNotifier());
        service.registerObserver(new OfficerNotifier());
        ScholarshipFactory factory = new ScholarshipFactory(converter);

        // View side
        JTabbedPane tabs = new JTabbedPane();
        ApplyPanel applyPanel = new ApplyPanel();
        ResultPanel resultPanel = new ResultPanel();
        tabs.addTab("Apply", applyPanel);
        tabs.addTab("Result", resultPanel);

        // Controller side
        ApplicationController controller =
                new ApplicationController(service, factory, resultPanel);

        // Wire the button: on click, process the form and switch to the Result tab.
        applyPanel.onSubmit(event -> {
            controller.submit(
                    applyPanel.getName(),
                    applyPanel.getGpaText(),
                    applyPanel.getIncomeText(),
                    applyPanel.getCurrency(),
                    applyPanel.getScholarshipName());
            tabs.setSelectedComponent(resultPanel);
        });

        add(tabs);
    }
}