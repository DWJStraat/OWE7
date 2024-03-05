package org.fasta;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FastaReaderTest {
    private final FastaReader fastaReader = new FastaReader("src/test/resources/test.fasta");

    FastaReaderTest() throws IOException {
    }

    @org.junit.jupiter.api.Test
    void testFastaReader() {
        assertEquals(2, fastaReader.fastas.size());
    }

    @org.junit.jupiter.api.Test
    void testFastaReaderType() {
        assertEquals("DNA", fastaReader.fastas.get(0).getType());
        assertEquals("Protein", fastaReader.fastas.get(1).getType());
    }

    @org.junit.jupiter.api.Test
    void testFastaReaderBlast() throws InterruptedException {
        fastaReader.blast();
        assertEquals(2, fastaReader.fastas.size());
        System.out.println(fastaReader.fastas);
    }
}
