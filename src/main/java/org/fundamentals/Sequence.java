package org.fundamentals;

import org.biojava.nbio.ws.alignment.qblast.BlastProgramEnum;

public class Sequence {
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

    @Override
    public String toString() {
        return type;
    }

}
