package org.gui;

import org.headacheRemoval.createWindow;

import javax.swing.*;

public class loadingScreen {
    private static final createWindow window = new createWindow("Loading", 200, 250);
    private static final JPanel visual = new JPanel();
    private static final JLabel label = new JLabel("Loading...", SwingConstants.CENTER);
    private static final JButton next = new JButton("Export");
    public static void Main() {
        build();
    }

    private static void build() {
        visual.setBackground(java.awt.Color.WHITE);
        window.add(label, 10, 10, 150, 40);
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
