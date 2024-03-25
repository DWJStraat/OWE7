package org.blast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

// This script is a dummy class to simulate the presence of a Blast class in the org.blast package for the purpose
// of testing the Database class.
public class DummyBlast extends Blast {


    public DummyBlast() throws ParserConfigurationException, IOException, SAXException {
        String sequence = "aggccgccactatgacagcgattgcgactgtgcagatttccacatgtacctgagccgctg";
        File file = new File("src/test/resources/blast.xml");
        InputStream data = null;
        try {
            data = file.toURI().toURL().openStream();
            new java.io.InputStreamReader(data);
        } catch (Exception e) {
            Logger log = Logger.getLogger("org.blast.DummyBlast");
            log.severe("Error: " + e);
        }
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(data);
        Element root = doc.getDocumentElement();
        NodeList hitNodes = root.getElementsByTagName("Hit");
        for (int i = 0; i < hitNodes.getLength(); i++) {
            Node hit = hitNodes.item(i);
            hits.add(new BlastHit(hit, sequence));
        }
    }

}
