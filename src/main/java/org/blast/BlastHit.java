package org.blast;

import org.w3c.dom.Node;

public class BlastHit{
    public final Float bitScore;
    public final int score;
    public final String eval;
    public final int identity;
    public final int positives;
    public final int gaps;
    public final int length;
    public final String accession;
    public BlastHit (Node node) {
        // parse the XML node and populate the object
        bitScore = Float.parseFloat(node.getAttributes().getNamedItem("bit_score").getNodeValue());
        score = Integer.parseInt(node.getAttributes().getNamedItem("score").getNodeValue());
        eval = node.getAttributes().getNamedItem("eval").getNodeValue();
        identity = Integer.parseInt(node.getAttributes().getNamedItem("identity").getNodeValue());
        positives = Integer.parseInt(node.getAttributes().getNamedItem("positives").getNodeValue());
        gaps = Integer.parseInt(node.getAttributes().getNamedItem("gaps").getNodeValue());
        length = Integer.parseInt(node.getAttributes().getNamedItem("length").getNodeValue());
        accession = node.getAttributes().getNamedItem("accession").getNodeValue();
    }

//    public void toDb(){
//        // save the object to the database
//        Database.saveBlastHit(this);
//    }
}
