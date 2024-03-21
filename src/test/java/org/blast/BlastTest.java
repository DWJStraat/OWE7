package org.blast;

import org.fundamentals.Sequence;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

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

    @Test
    void testBlastOutput() throws InterruptedException, IOException {
        Sequence seq = new Sequence("aggccgccactatgacagcgattgcgactgtgcagatttccacatgtacctgagccgctg");
        Blast blast = new Blast(seq);
        blast.output();
    }

    @Test
    void testBlastRender() throws InterruptedException, ParserConfigurationException, IOException, SAXException {
        Sequence seq = new Sequence("aggccgccactatgacagcgattgcgactgtgcagatttccacatgtacctgagccgctg");
        Blast blast = new Blast(seq);
        blast.render();
    }

}