package org.gui;

import org.headacheRemoval.createWindow;

import javax.swing.*;

public class loadingScreen {
    private static final createWindow window = new createWindow("Loading", 200, 250);
    private static final JPanel visual = new JPanel();
    public static final JLabel label = new JLabel("Loading...");
    private static final JButton next = new JButton("Export");
    public static void Main() {
        build();
    }

    private static void build() {
        window.add(label, 10, 10, 140, 40);
        window.show();
    }

    static void done() {
        nextBuild();
        label.setText("Done. Press the button to go to export");
        window.add(next, 10, 60, 150, 40);
    }

    private static void nextBuild() {
        next.addActionListener(e -> {
            window.close();
            ExportScreen.main();
        });
    }
}
