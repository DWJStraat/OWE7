package org.fasta;

import org.blast.Blast;

public class Fasta {
    public final String head;
    public final String seq;
    public Blast blastObject;
    public String getType() {
        return type;
    }

    private String type;
    public Fasta (String header, String sequence) {
        head = header;
        seq = sequence;
        identifier();
    }

    private void identifier () {
        if (seq.charAt(0) == 'M') {
            type = "Protein";
        } else {
            type = "DNA";
        }
    }

    public void blast() throws InterruptedException {
        blastObject = new Blast(seq);
    }




}
