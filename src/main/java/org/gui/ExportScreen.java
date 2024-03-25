package org.gui;

import org.fundamentals.Database;
import org.headacheRemoval.createWindow;

import javax.swing.*;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ExportScreen {
    private static final createWindow window = new createWindow("Export", 200, 250);
    private static final JPanel visual = new JPanel();
    private static final JButton export = new JButton("Export");
    private static final JButton back = new JButton("Back");
    private static final Logger log = Logger.getLogger("org.gui.ExportScreen");
    public static void main() {
        build();
    }

    public static void build() {
        exportBuild();
        backBuild();
        window.show();

    }

    private static void backBuild() {
        back.addActionListener(e -> {
            window.close();
            UploadScreen.Main();
        });
        window.add(back, 10, 60, 150, 40);
    }

    private static void exportBuild() {
        export.addActionListener(e -> {
            try {
                Database.exportDb();
            } catch (SQLException ex) {
                window.error("An error occurred while exporting the database");
                log.log(java.util.logging.Level.SEVERE, "An error occurred while exporting the database", ex);
                log.log(java.util.logging.Level.SEVERE, "{}", ex.getCause());
            }
        });
        window.add(export, 10, 10, 150, 40);
    }
}
