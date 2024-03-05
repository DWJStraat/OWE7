package org.blast;

import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastAlignmentProperties;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastOutputProperties;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastService;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Blast {
    private final String sequence;
    private final Logger log = Logger.getLogger("org.blast.Blast");
    private final NCBIQBlastService service = new NCBIQBlastService();
    private final NCBIQBlastAlignmentProperties props = new NCBIQBlastAlignmentProperties();
    private final NCBIQBlastOutputProperties outprops = new NCBIQBlastOutputProperties();
    private String reqId = null;
    private InputStream data = null;
    private BufferedReader reader = null;
    public final List<BlastHit> hits = new ArrayList<>();


    public Blast(String seq) throws InterruptedException {
        log.log(Level.WARNING, "BLAST currently broken");
        sequence = seq;

//        props.setBlastProgram(BlastProgramEnum.blastp);
//        props.setBlastDatabase("nr");
//        outprops.setOutputOption(BlastOutputParameterEnum.ALIGNMENTS, "200");
//        try {
//            reqId = service.sendAlignmentRequest(sequence, props);
//            while (!service.isReady(reqId)) {
//                log.log(Level.INFO, "Waiting for results for {0}. Sleeping for 5 seconds", reqId );
//                Thread.sleep(5000);
//            }
//
//            data = service.getAlignmentResults(reqId, outprops);
//            reader = new BufferedReader(new InputStreamReader(data));
//        }
//        catch (InterruptedException e) {
//            log.log(Level.SEVERE,"Thread interrupted {0}", e.getMessage());
//            log.severe("Cause: " + e.getCause());
//            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
//            Thread.currentThread().interrupt();
//        }
//        catch (Exception e) {
//            log.severe("Error occurred: " + e.getMessage());
//            log.severe("Cause: " + e.getCause());
//            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
//        }
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
        try (FileWriter fw = new FileWriter(f);
             BufferedWriter bw = new BufferedWriter(fw)) {
            String line;
            while ((line = reader.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }
            
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}
