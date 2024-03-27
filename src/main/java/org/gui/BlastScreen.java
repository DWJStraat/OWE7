package org.gui;

import org.fasta.FastaBucket;
import org.fundamentals.blastThread;
import org.headacheRemoval.createWindow;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BlastScreen {
    public static Boolean proc = false;
    private static final Logger log = Logger.getLogger("org.gui.BlastScreen");
    private static final createWindow window = new createWindow("Blast", 200, 250);
    private static final JPanel visual = new JPanel();
    private static final JButton blast = new JButton("Blast");

    private static final JLabel label = new JLabel("Click to blast");
    public static void Main() {
        log.log(java.util.logging.Level.INFO, "Starting BlastScreen");
        build();
    }

    private static void build() {
        blastBuild();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        visual.setBackground(java.awt.Color.WHITE);
        window.add(label, 10, 10, 150, 40);
        window.add(blast, 10, 60, 100, 40);

        String seqLabel = "Sequences: " + FastaBucket.size();
        String orfLabel = "Number of ORFs: " + FastaBucket.ORFs();
        String fileLabel =  "Files: " + FastaBucket.size();
        window.add(new JLabel(fileLabel), 10, 145, 150, 40);
        window.add(new JLabel(orfLabel), 10, 160, 150, 40);
        window.add(new JLabel(seqLabel), 10, 175, 150, 40);
        window.show();
    }

    private static void blastBuild() {
        blast.addActionListener(e -> {
            blast.setEnabled(false);
            log.log(Level.INFO, "Blasting");
            label.setText("Blasting...");
            window.error("Blasting started");
            blastThread blastThread = new blastThread();
            Thread thread = new Thread(blastThread);
            thread.start();
            Thread cont = new Thread(() -> {
                int i = 0;
                int sleepyTime = 10000;
                while(!proc) {
                    try {
                        Thread.sleep(sleepyTime);
                        i = i + sleepyTime/1000;
                        sleepyTime = sleepyTime + sleepyTime/10;
                        label.setText("Blasting... " + i + "s");

                    } catch (InterruptedException ex) {
                        log.log(Level.SEVERE, "Thread Error", ex);
                    }
                }
            });
            cont.start();
            window.close();
            UploadScreen.Main();

        });
    }


}
