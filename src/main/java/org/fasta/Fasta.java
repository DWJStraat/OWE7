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

    public void blast() throws InterruptedException {
        blastObject = new Blast(sequence);
    }

//    public void blastORFs() throws InterruptedException {
//        ArrayList<String> orfs = sequence.getOrfs();
//        for (String orf : orfs) {
//            blastArrayList.add(new Blast(new Sequence(orf)));
//        }
//
//    }


    public String getHash() {
        return Integer.toString(Integer.parseInt(sequence.getHash())+ head.hashCode());
    }


}
