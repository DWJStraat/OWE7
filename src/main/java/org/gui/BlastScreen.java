package org.gui;

import org.headacheRemoval.createWindow;
import org.fasta.FastaBucket;
import javax.swing.*;
import java.util.logging.Logger;

public class BlastScreen {
    private static final Logger log = Logger.getLogger("org.gui.BlastScreen");
    private static final createWindow window = new createWindow("Blast", 200, 250);
    private static final JPanel visual = new JPanel();
    private static final JButton blast = new JButton("Blast");
    private static final JButton back = new JButton("Back");
    public static void Main() {
        log.log(java.util.logging.Level.INFO, "Starting BlastScreen");
        build();
    }

    private static void build() {
        blastBuild();
//        backBuild();
        visual.setBackground(java.awt.Color.WHITE);
        window.add(new JLabel("Click to blast"), 10, 10, 150, 40);
        window.add(blast, 10, 60, 100, 40);
        window.add(back, 10, 110, 100, 40);
        String seqLabel = "Sequences: " + FastaBucket.size();
        String orfLabel = "ORF: " + FastaBucket.ORFs();
        String fileLabel =  "Files: " + FastaBucket.size();
        window.add(new JLabel(fileLabel), 10, 145, 150, 40);
        window.add(new JLabel(orfLabel), 10, 160, 150, 40);
        window.add(new JLabel(seqLabel), 10, 175, 150, 40);
        window.show();
    }

    private static void blastBuild() {
        blast.addActionListener(e -> {
            loadingScreen.Main();
            log.log(java.util.logging.Level.INFO, "Blasting");
            try {
                FastaBucket.blast();
            } catch (InterruptedException ex) {
                window.error("Blast failed");
                log.log(java.util.logging.Level.SEVERE, "Blast failed", ex);
            }
            loadingScreen.done();
            log.log(java.util.logging.Level.INFO, "Blast finished");
            window.close();
        });
    }
}
