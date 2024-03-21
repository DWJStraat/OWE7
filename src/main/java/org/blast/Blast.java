package org.blast;

import org.biojava.nbio.ws.alignment.qblast.*;
import org.fundamentals.Database;
import org.fundamentals.Sequence;
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
    public final String sequence;
    private final Logger log = Logger.getLogger("org.blast.Blast");
    private final NCBIQBlastService service = new NCBIQBlastService();
    private final NCBIQBlastAlignmentProperties props = new NCBIQBlastAlignmentProperties();
    private final NCBIQBlastOutputProperties outprops = new NCBIQBlastOutputProperties();
    private String reqId = null;
    private InputStream data = null;
    private BufferedReader reader = null;
    public List<BlastHit> hits = new ArrayList<>();

    public Blast(String seq) {
        sequence = seq;
        props.setBlastProgram(BlastProgramEnum.blastp);
        init();
    }
    public Blast(String seq, Sequence seqtype) throws InterruptedException {
        sequence = seq;
        props.setBlastProgram(seqtype.getBlastProgram());
        System.out.println("Blast program: " + seqtype.getBlastProgram());
        init();
    }
    public Blast(Sequence seq) throws InterruptedException{
        sequence = seq.seq;
        props.setBlastProgram(seq.getBlastProgram());
        init();
    }

    public Blast() {
        sequence = "aggccgccactatgacagcgattgcgactgtgcagatttccacatgtacctgagccgctg";
    }


    public void init(){
        long startTime = System.currentTimeMillis();
        int sleepyTime = 5000;
        props.setBlastDatabase("nr");
        outprops.setOutputOption(BlastOutputParameterEnum.ALIGNMENTS, "200");
        try {
            reqId = service.sendAlignmentRequest(sequence, props);
            if (reqId != null) {
                reqId = service.sendAlignmentRequest(sequence, props);
            }
            while (!service.isReady(reqId)) {
                log.log(Level.INFO, "Waiting for results for {0}.", reqId);
                log.log(Level.INFO, "Sleeping for {0}ms", sleepyTime);
                Thread.sleep(sleepyTime);
                sleepyTime = sleepyTime + sleepyTime/5;
            }

            data = service.getAlignmentResults(reqId, outprops);
            reader = new BufferedReader(new InputStreamReader(data));
        }
        catch (InterruptedException e) {
            log.log(Level.SEVERE,"Thread interrupted {0}", e.getMessage());
            log.severe("Cause: " + e.getCause());
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        catch (Exception e) {
            if (reqId == null) {
                log.severe("Request ID is null");
            }
            log.severe("Error occurred: " + e.getMessage());
            log.severe("Cause: " + e.getCause());
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
//        try{
//            render();
//        } catch (ParserConfigurationException | IOException | SAXException e) {
//            log.log(Level.SEVERE, "Error occurred: {0}", e.getMessage());
//            log.severe("Cause: " + e.getCause());
//            e.printStackTrace();
//        }
        log.log(Level.INFO, "Time taken: {0}ms", endTime - startTime);
    }

    public void render () throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(data);
        NodeList xmlhits = doc.getElementsByTagName("Hit");

        for (int i = 0; i < xmlhits.getLength(); i++) {
            hits.add(new BlastHit(xmlhits.item(i), sequence));
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

    public void save() {
        Database.saveBlast(this);
    }
}
