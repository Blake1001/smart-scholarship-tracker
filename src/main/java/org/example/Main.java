package org.example;

import org.example.gui.MainWindow;

import javax.swing.SwingUtilities;

/**
 * Application entry point. It starts the graphical user interface.
 */
public class Main {

    public static void main(String[] args) {
        // Start the GUI safely on Swing's event-dispatch thread.
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }
}