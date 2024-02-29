package org.fasta;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FastaTest {
    @org.junit.jupiter.api.Test
    void testFastaIdentifyDna() {
        Fasta dna = new Fasta(">dna", "ATG");
        assertEquals("DNA", dna.getType());
    }

    @org.junit.jupiter.api.Test
    void testFastaIdentifyProt() {
        Fasta dna = new Fasta(">prot", "MAA");
        assertEquals("Protein", dna.getType());
    }
}