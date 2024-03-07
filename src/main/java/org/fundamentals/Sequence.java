package org.fundamentals;

import org.biojava.nbio.ws.alignment.qblast.BlastProgramEnum;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sequence {
    private static final Pattern orfPattern = Pattern.compile(
            "(?=(ATG(\\w\\w\\w)*((TAA)|(TAG)|(TGA))))",
            Pattern.CASE_INSENSITIVE
    );

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

    public ArrayList getOrfs() {
        ArrayList<String> orfs = new ArrayList<>();
        Matcher forward = orfPattern.matcher(seq);
        while (forward.find()) {
            orfs.add(forward.group());
        }
        Matcher reverse = orfPattern.matcher(seq[]);
    }



    @Override
    public String toString() {
        return type;
    }

}
