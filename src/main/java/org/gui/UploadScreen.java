package org.gui;

import org.fasta.FastaBucket;
import org.headacheRemoval.createWindow;
import org.headacheRemoval.openFile;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UploadScreen {
    private static final Logger log = Logger.getLogger("org.gui.UploadScreen");
    private static final createWindow window = new createWindow("Upload", 200, 200);
    private static openFile openFile;
    private static final JPanel visual = new JPanel();
    private static final JButton open = new JButton("Open");
    private static final JButton next = new JButton("Next");
    private static final JButton export = new JButton("Export");

    public static void Main() {
        log.log(Level.INFO, "Starting UploadScreen");
        build();
    }

    private static void build() {
        openBuild();
        nextBuild();
        exportBuild();
        visual.setBackground(Color.WHITE);
        window.add(new JLabel("Select a file to upload"), 10, 10, 150, 40);
        window.add(open, 10, 60, 100, 40);
        window.add(next, 10, 110, 100, 40);
        window.show();

    }

    private static void exportBuild() {
        export.addActionListener(e -> {
            window.close();
            ExportScreen.main();
        });
    }

    private static void openBuild() {
        open.addActionListener(e -> {
            try {
                openFile = new openFile();
                log.log(Level.INFO, "Finished loading file {0}", openFile.file.getName());
                FastaBucket.add(openFile.reader);
            } catch (IOException ex) {
                window.error("No File Selected");
            } catch (IllegalArgumentException ex) {
                window.error("File already loaded");
            } catch (RuntimeException ex) {
                window.error(ex.getMessage());
                log.log(Level.SEVERE, "An error occurred : {0}", ex);
                log.log(Level.SEVERE, "{}", ex.getCause());
                log.log(Level.SEVERE, "{}", ex.getStackTrace());
            } catch (Exception ex) {
                window.error("An error occurred");
                log.log(Level.SEVERE, "An error occurred", ex);
            }
        });
    }

    private static void nextBuild() {
        next.addActionListener(e -> {
            if(FastaBucket.isEmpty()) {
                window.error("No file loaded");
                return;
            }
            window.close();
            BlastScreen.Main();
        });
    }
}


