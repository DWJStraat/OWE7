package org.gui;

import org.headacheRemoval.createWindow;

import javax.swing.*;

public class ExportScreen {
    private static final createWindow window = new createWindow("Export", 200, 250);
    private static final JPanel visual = new JPanel();
    private static final JButton export = new JButton("Export");
    public static void main() {
        build();
    }

    public static void build() {
        exportBuild();

        window.show();

    }

    private static void exportBuild() {
        export.addActionListener(e -> {
            window.close();
        });
        window.add(export, 10, 10, 150, 40);
    }
}
