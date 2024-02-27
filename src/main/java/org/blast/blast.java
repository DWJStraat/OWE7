package org.blast;
import static org.biojava.nbio.ws.alignment.qblast.BlastAlignmentParameterEnum.ENTREZ_QUERY;
import java.io.*;
import org.biojava.nbio.core.sequence.io.util.IOUtils;
import org.biojava.nbio.ws.alignment.qblast.*;

public class blast {
    private final String sequence;
    private final NCBIQBlastService service = new NCBIQBlastService();
    private final NCBIQBlastAlignmentProperties props = new NCBIQBlastAlignmentProperties();
    private final NCBIQBlastOutputProperties outprops = new NCBIQBlastOutputProperties();
    private final String reqId;
    private final InputStream data;
    private final BufferedReader reader;
    public void blast (String seq){
        sequence = seq;
        props.setBlastProgram(BlastProgramEnum.blastp);
        props.setBlastDatabase("nr");
        outprops.setOutputOption(BlastOutputEnum.ALIGNMENTS, "200");
        try {
            reqId = service.sendAlignmentRequest(sequence, props);
            while (!service.isReady(reqId)) {
                System.out.println("Waiting for results for " + reqID+". Sleeping for 5 seconds");
                Thread.sleep(5000);
            }

            data = service.getAlignmentResults(reqId, outprops);
            reader = new BufferedReader(new InputStreamReader(data));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.close(data);
        }
    }
}
