package org.gui;

import org.headacheRemoval.createWindow;

import javax.swing.*;

public class LoadingScreen {
    private static final createWindow window = new createWindow("Loading", 200, 250);
    public static final JLabel label = new JLabel("Loading...");
    private static final JButton next = new JButton("Export");
    public static void Main() {
        build();
    }

    private static void build() {
        window.add(label, 10, 10, 140, 40);
        window.show();
    }


    private static void nextBuild() {
        next.addActionListener(e -> {
            window.close();
            ExportScreen.Main();
        });
    }
}
