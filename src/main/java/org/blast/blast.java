package org.blast;

import org.biojava.nbio.ws.alignment.qblast.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class blast {
    private String sequence;
    private final NCBIQBlastService service = new NCBIQBlastService();
    private final NCBIQBlastAlignmentProperties props = new NCBIQBlastAlignmentProperties();
    private final NCBIQBlastOutputProperties outprops = new NCBIQBlastOutputProperties();
    private final String reqId;
    private final InputStream data;
    private final BufferedReader reader;

    public blast(String reqId, InputStream data, BufferedReader reader) {
        this.reqId = reqId;
        this.data = data;
        this.reader = reader;
    }

    public blast(String seq){
        sequence = seq;
        props.setBlastProgram(BlastProgramEnum.blastp);
        props.setBlastDatabase("nr");
        outprops.setOutputOption(BlastOutputParameterEnum.ALIGNMENTS, "200");
        try {
            reqId = service.sendAlignmentRequest(sequence, props);
            while (!service.isReady(reqId)) {
                System.out.println("Waiting for results for " + reqId +". Sleeping for 5 seconds");
                Thread.sleep(5000);
            }

            data = service.getAlignmentResults(reqId, outprops);
            reader = new BufferedReader(new InputStreamReader(data));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
