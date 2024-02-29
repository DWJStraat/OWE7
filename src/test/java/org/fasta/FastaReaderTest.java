package org.fasta;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FastaReaderTest {
        @org.junit.jupiter.api.Test
        void testFastaReader() throws IOException {
            FastaReader fastaReader = new FastaReader("src/test/resources/test.fasta");
            assertEquals(2, fastaReader.fastas.size());
        }

        @org.junit.jupiter.api.Test
        void testFastaReaderType() throws IOException {
            FastaReader fastaReader = new FastaReader("src/test/resources/test.fasta");
            assertEquals("DNA", fastaReader.fastas.get(0).getType());
            assertEquals("Protein", fastaReader.fastas.get(1).getType());
        }




}