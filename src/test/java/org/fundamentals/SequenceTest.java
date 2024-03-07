package org.fundamentals;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SequenceTest {
    private static Sequence seq = new Sequence("CCCATGAAATAGCCC");
    private static String testseq = "ACGT";

    @Test
    void getOrfs() {
        System.out.println(seq.getOrfs());
    }

    @Test
    void reverse() {
        String test = seq.reverse(testseq);
        assertEquals("TCGA", test);
    }

    @Test
    void complementSequence() {
        assertEquals("ACGU", seq.complementSequence(testseq));
    }

    @Test
    void complementReverse() {
        assertEquals("UGCA", seq.complementReverse(testseq));
    }
}