package org.fasta;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FastaReaderTest {
        @org.junit.jupiter.api.Test
        void testFastaReader() throws IOException {
            FastaReader fastaReader = new FastaReader("src/test/resources/test.fasta");
            assertEquals(2, fastaReader.fastas.size());
        }


}