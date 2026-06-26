package org.example.gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

/**
 * The "Apply" screen: a form where the user enters the applicant's details
 * and picks a scholarship. It only collects input (a View component).
 */
public class ApplyPanel extends JPanel {

    private final JTextField nameField = new JTextField();
    private final JTextField gpaField = new JTextField();
    private final JTextField incomeField = new JTextField();
    private final JComboBox<String> currencyBox =
            new JComboBox<>(new String[]{"MYR", "USD", "EUR", "GBP"});
    private final JComboBox<String> scholarshipBox =
            new JComboBox<>(new String[]{"Merit Scholarship", "Need-Based Scholarship", "Merit + Need Scholarship"});
    private final JButton submitButton = new JButton("Check Eligibility");

    /** Builds the form layout with labelled input fields. */
    public ApplyPanel() {
        setLayout(new GridLayout(7, 2, 10, 10));

        add(new JLabel("Full name:"));
        add(nameField);
        add(new JLabel("GPA (0.0 - 4.0):"));
        add(gpaField);
        add(new JLabel("Annual income:"));
        add(incomeField);
        add(new JLabel("Income currency:"));
        add(currencyBox);
        add(new JLabel("Scholarship:"));
        add(scholarshipBox);
        add(new JLabel(""));
        add(submitButton);
    }

    /**
     * Registers the action to run when the user clicks "Check Eligibility".
     *
     * @param listener the action to run
     */
    public void onSubmit(ActionListener listener) {
        submitButton.addActionListener(listener);
    }

    public String getName() {
        return nameField.getText();
    }

    public String getGpaText() {
        return gpaField.getText();
    }

    public String getIncomeText() {
        return incomeField.getText();
    }

    public String getCurrency() {
        return (String) currencyBox.getSelectedItem();
    }

    public String getScholarshipName() {
        return (String) scholarshipBox.getSelectedItem();
    }
}