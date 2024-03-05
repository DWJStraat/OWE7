package org.blast;

import org.fundamentals.Sequence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlastTest {
    @Test
    void testBlast() throws InterruptedException {
        Blast blast = new Blast("MKWVTFISLLFLFSSAYSRGVFRRDAHKSEVAHRFKDLGEENFKALVLIAFAQYLQQCP");
        assertNotNull(blast);
    }

    @Test
    void testBlastDna() throws InterruptedException {
        Sequence seq = new Sequence("aggccgccactatgacagcgattgcgactgtgcagatttccacatgtacctgagccgctg");
        Blast blast = new Blast(seq);
        assertNotNull(blast);
    }

}