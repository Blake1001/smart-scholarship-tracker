package org.example.gui;

import org.example.model.ApplicationStatus;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

/**
 * The "Result" screen: shows the outcome of the most recent eligibility check
 * (status in colour, plus a short reason). This is a View component.
 */
public class ResultPanel extends JPanel {

    private static final int TITLE_FONT_SIZE = 22;
    private static final Color GREEN = new Color(0, 140, 0);
    private static final Color RED = new Color(180, 0, 0);

    private final JLabel statusLabel = new JLabel("No result yet", SwingConstants.CENTER);
    private final JLabel reasonLabel = new JLabel("", SwingConstants.CENTER);

    /** Builds the result layout. */
    public ResultPanel() {
        setLayout(new BorderLayout(10, 20));
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, TITLE_FONT_SIZE));
        add(statusLabel, BorderLayout.CENTER);
        add(reasonLabel, BorderLayout.SOUTH);
    }

    /**
     * Updates the screen with a new result.
     *
     * @param status the decided status
     * @param reason the explanation to display
     */
    public void showResult(ApplicationStatus status, String reason) {
        statusLabel.setText(status.toString());
        statusLabel.setForeground(status == ApplicationStatus.ELIGIBLE ? GREEN : RED);
        reasonLabel.setText(reason);
    }

    /**
     * Shows an error message (for example, when the input is invalid or the
     * currency service is unavailable).
     *
     * @param message the message to display
     */
    public void showError(String message) {
        statusLabel.setText("Error");
        statusLabel.setForeground(RED);
        reasonLabel.setText(message);
    }
}