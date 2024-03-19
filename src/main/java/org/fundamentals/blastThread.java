package org.fundamentals;

import org.fasta.FastaBucket;
import org.gui.BlastScreen;

import java.util.logging.Level;
import java.util.logging.Logger;
// with thanks to https://rollbar.com/blog/java-illegalmonitorstateexception/

public class blastThread implements Runnable {
    private final Logger log = Logger.getLogger("org.fundamentals.blastThread");
    public blastThread() {
    }
    public void run() {
        synchronized (this){
            try{
                FastaBucket.blastThread();
                BlastScreen.proc = true;
            } catch (Exception e) {
                log.log(Level.SEVERE, "Thread Error failed", e);
            }
        }
    }
}
