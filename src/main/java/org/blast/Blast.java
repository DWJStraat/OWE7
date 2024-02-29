package org.blast;

import org.biojava.nbio.ws.alignment.qblast.*;
import org.logger.Log;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Blast {
    private final String sequence;
    private final Log log = new Log(Blast.class.getName());
    private final NCBIQBlastService service = new NCBIQBlastService();
    private final NCBIQBlastAlignmentProperties props = new NCBIQBlastAlignmentProperties();
    private final NCBIQBlastOutputProperties outprops = new NCBIQBlastOutputProperties();
    private String reqId = null;
    private InputStream data = null;
    private BufferedReader reader = null;
    public final List<BlastHit> hits = new ArrayList<>();


    public Blast(String seq) throws InterruptedException {
        sequence = seq;
        props.setBlastProgram(BlastProgramEnum.blastp);
        props.setBlastDatabase("nr");
        outprops.setOutputOption(BlastOutputParameterEnum.ALIGNMENTS, "200");
        try {
            reqId = service.sendAlignmentRequest(sequence, props);
            while (!service.isReady(reqId)) {
                log.info("Waiting for results for " + reqId +". Sleeping for 5 seconds");
                Thread.sleep(5000);
            }

            data = service.getAlignmentResults(reqId, outprops);
            reader = new BufferedReader(new InputStreamReader(data));
        } 
        catch (InterruptedException e) {
            log.error("Thread interrupted");
            throw new InterruptedException(e.getMessage());
        }
        catch (Exception e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    public void render () throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(data);
        NodeList xmlhits = doc.getElementsByTagName("Hit");

        for (int i = 0; i < xmlhits.getLength(); i++) {
            hits.add(new BlastHit(xmlhits.item(i)));
        }
    }

    public void output () throws IOException {
        log.info("Writing to file");
        File f = new File("blast_output.txt");
        try {
            FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            String line;
            while ((line = reader.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }
            bw.close();
            
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}
