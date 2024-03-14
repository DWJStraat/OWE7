package org.fundamentals;

import org.biojava.nbio.ws.alignment.qblast.BlastProgramEnum;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sequence {
    private ArrayList<String> orfs;

    public final String type;
    public final String seq;
    public Sequence(String sequence) {
        seq = sequence;
        if (sequence.charAt(0) == 'M') {
            type = "Protein";
        } else {
            type = "DNA";
        }
    }

    public BlastProgramEnum getBlastProgram() {
        if (type.equals("Protein")) {
            return BlastProgramEnum.blastp;
        } else {
            return BlastProgramEnum.blastn;
        }
    }

    public ArrayList<String> getOrfs() {
        orfs = new ArrayList<>();
        orfAdder(seq);
        orfAdder(reverse(seq));
        orfAdder(complementSequence(seq));
        orfAdder(complementReverse(seq));
        return orfs;
    }

    private void orfAdder(String seq){
        Matcher hits = findOrf(seq);
        System.out.println(hits);
        while(hits.find()){
            orfs.add(hits.group());
        }
    }

    public Matcher findOrf (String seq) {
        String regex = "(A[TU]G(?:.{3})+?[TU](?:AG|AA|GA))";
        Pattern orfPattern = Pattern.compile(
                regex
        );
        return orfPattern.matcher(seq);
    }

    public String reverse (String seq) {
        StringBuilder reverseSeq = new StringBuilder();
        reverseSeq.append(seq);
        reverseSeq.reverse();
        return reverseSeq.toString();
    }

    public String complementSequence (String seq) {
        StringBuilder output = new StringBuilder();
        for(int i =0; i < seq.length(); i++){
            output.append(complement(seq.charAt(i)));
         }
        return output.toString();
    }

    private String complement (Character a) {
        return switch (a.toString()) {
            case "A" -> "U";
            case "T" -> "A";
            case "C" -> "G";
            case "G" -> "C";
            default -> null;
        };
    }

    public String complementReverse (String seq) {
        return complementSequence(reverse(seq));
    }



    @Override
    public String toString() {
        return type;
    }

}
