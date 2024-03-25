package org.fasta;

import java.util.ArrayList;

public class FastaBucket {
    private static final ArrayList<FastaReader> fastaList = new ArrayList<>();
    private static final ArrayList<String> hashList = new ArrayList<>();

    public static void add(FastaReader reader) throws IllegalArgumentException{
        if (hashList.contains(reader.getHash())){
            throw new IllegalArgumentException("File already exists in the bucket");
        }
        hashList.add(reader.getHash());
        fastaList.add(reader);
    }


    public static boolean isEmpty() {
        return fastaList.isEmpty();
    }

    public static Integer size() {
        return fastaList.size();
    }

    public static String ORFs() {
        Integer count = 0;
        for (FastaReader o : fastaList) {
            count += o.ORFs();
        }
        return count.toString();
    }

    public static void blast() {
        Thread blasting = new Thread(() -> {
            try {
                blastThread();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void blastThread() throws InterruptedException {
        for (FastaReader o : fastaList) {
            o.blast();
        }
    }
}
