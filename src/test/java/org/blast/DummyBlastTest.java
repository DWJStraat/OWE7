package org.blast;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DummyBlastTest {

    @Test
    void testBlast() throws ParserConfigurationException, IOException, SAXException {
        Blast blast = new DummyBlast();
        assertEquals(blast.hits.size(), 100);
    }

    @Test
    void testSave() throws ParserConfigurationException, IOException, SAXException, SQLException {
        Blast blast = new DummyBlast();
        blast.save();
    }

}