package org.example.gui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 * The main application window. It holds the three screens (Apply, Result,
 * History) as tabs. This is the top-level View in the MVC structure.
 */
public class MainWindow extends JFrame {

    private static final int WINDOW_WIDTH = 640;
    private static final int WINDOW_HEIGHT = 480;

    /**
     * Builds the main window and adds the application screens as tabs.
     */
    public MainWindow() {
        setTitle("Smart Scholarship Tracker");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Apply", new ApplyPanel());

        add(tabs);
    }
}