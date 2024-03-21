package org.blast;

import org.w3c.dom.Element;
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
    public final String seq;
    private final Element hitElement;
    private final Element hspElement;
    public BlastHit (Node node, String sequence) {
        Node hit = node;
        hitElement = (Element) hit;
        Node hsps = hitElement.getElementsByTagName("Hsp").item(0);
        hspElement = (Element) hsps;
        bitScore = Float.parseFloat(hspGet("Hsp_bit-score"));
        score = Integer.parseInt(hspGet("Hsp_score"));
        eval = hspGet("Hsp_evalue");
        identity = Integer.parseInt(hspGet("Hsp_identity"));
        positives = Integer.parseInt(hspGet("Hsp_positive"));
        gaps = Integer.parseInt(hspGet("Hsp_gaps"));
        length = Integer.parseInt(hspGet("Hsp_align-len"));
        accession = hitGet("Hit_accession");
        seq = sequence;
    }

    private String hitGet(String tag){
        return hitElement.getElementsByTagName(tag).item(0).getTextContent();
    }

    private String hspGet(String tag){
        return hspElement.getElementsByTagName(tag).item(0).getTextContent();
    }






}
