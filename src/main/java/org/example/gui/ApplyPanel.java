package org.example.gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.GridLayout;

/**
 * The "Apply" screen: a form where the user enters the applicant's details
 * and picks a scholarship. This is a View component; it only collects input
 * and shows fields. The actual processing will be wired in a later batch.
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

    /**
     * Builds the form layout with labelled input fields.
     */
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
}