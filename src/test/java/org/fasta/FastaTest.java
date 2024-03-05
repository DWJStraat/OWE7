package org.fasta;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FastaTest {
    @Test
    void testFastaIdentifyDna() {
        Fasta dna = new Fasta(">dna", "ATG");
        assertEquals("DNA", dna.getType());
    }

    @Test
    void testFastaIdentifyProt() {
        Fasta dna = new Fasta(">prot", "MAA");
        assertEquals("Protein", dna.getType());
    }

    @Test
    void testFastaBlast() throws InterruptedException {
        Fasta fasta = new Fasta(">prot", "MAA");
        fasta.blast();
        assertNotNull(fasta.blastObject);
    }
}