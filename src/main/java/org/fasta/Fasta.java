package org.fasta;

import org.blast.Blast;
import org.fundamentals.Sequence;

public class Fasta {
    public final String head;
    public Blast blastObject;
    public Sequence sequence;
    public Fasta (String header, String sequence) {
        head = header;
        this.sequence = new Sequence(sequence);
    }


    public String getType() {
        return sequence.toString();
    }

    public void blast() throws InterruptedException {
        blastObject = new Blast(sequence);
    }




}
