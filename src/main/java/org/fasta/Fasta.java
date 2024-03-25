package org.fasta;

import org.blast.Blast;
import org.fundamentals.Sequence;

import java.util.ArrayList;

public class Fasta {
    public final String head;

    public Blast getBlastObject() {
        return blastObject;
    }

    private Blast blastObject;

    private final ArrayList<Blast> blastArrayList = new ArrayList<>();
    public final Sequence sequence;
    public Fasta (String header, String sequence) {
        head = header;
        this.sequence = new Sequence(sequence);
        blastObject = null;
    }

    public String getName() {
        return head;
    }

    public String getType() {
        return sequence.toString();
    }

    public void blast() {
        blastObject = new Blast(sequence);
    }

    public String getHash() {
        return Integer.toString(Integer.parseInt(sequence.getHash())+ head.hashCode());
    }


}
