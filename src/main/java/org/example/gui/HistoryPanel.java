package org.example.gui;

import org.example.model.Application;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;

/**
 * The "History" screen: a table listing every application that has been
 * processed during this session (applicant, scholarship, and final status).
 * This is a View component.
 */
public class HistoryPanel extends JPanel {

    private final DefaultTableModel tableModel;

    /** Builds the history table with its column headings. */
    public HistoryPanel() {
        setLayout(new BorderLayout());

        String[] columns = {"Application ID", "Applicant", "Scholarship", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    /**
     * Adds one processed application as a new row in the table.
     *
     * @param application the application to record
     */
    public void addApplication(Application application) {
        tableModel.addRow(new Object[]{
                application.getId(),
                application.getApplicant().getName(),
                application.getScholarship().getName(),
                application.getStatus()
        });
    }
}