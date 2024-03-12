package org.fundamentals;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SequenceTest {
    private static final Sequence seq = new Sequence("CCCATGATAAAAAAATAGTACCC");
    private static final String testseq = "ACGT";

    @Test
    void getOrfs() {
        System.out.println(seq.getOrfs());
    }

    @Test
    void reverse() {
        String test = seq.reverse(testseq);
        assertEquals("TGCA", test);
    }

    @Test
    void complementSequence() {
        assertEquals("UGCA", seq.complementSequence(testseq));
    }

    @Test
    void complementReverse() {
        assertEquals("ACGU", seq.complementReverse(testseq));
    }

    @Test
    void getOrgHits() {
        Matcher m = seq.findOrf("FFFATGATGAAATAGFFFATGAAATAG");
        while (m.find()) {
            System.out.println(m.group());
        }
    }
}