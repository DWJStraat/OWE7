package org.fasta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FastaReader {
    private final Logger log = Logger.getLogger("org.fasta.FastaReader");
    final List<Fasta> fastas = new ArrayList<>();

    public FastaReader(String path) throws IOException {
        init(path);
    }

    public FastaReader (String path, Boolean blast) throws IOException, InterruptedException {
        init(path);
        // BLAST broken
        if (Boolean.TRUE.equals(blast)) {
            blast();
        }
    }

    public void blast () throws InterruptedException {
        for (Fasta fasta : fastas) {
            fasta.blast();
        }
    }

    private void init (String path) throws IOException {
        log.log(Level.INFO, "Reading file: {0}", path);
        File file = new File(path);
        String contents = new String(java.nio.file.Files.readAllBytes(file.toPath()));
        StringBuilder sequence = new StringBuilder();
        String header = "";
        for (String line : contents.split("\n")) {
            if (line.startsWith(">")) {
                if (!sequence.toString().isEmpty()) {
                    addFasta(header, sequence.toString());
                    sequence = new StringBuilder();
                }
                header = line;
            } else {
                line = line.replace(" ", "");
                line = line.replace("\n", "");
                sequence.append(line);
            }
        }
        addFasta(header, sequence.toString());
    }

    private void addFasta(String header, String sequence) {
        log.log(Level.INFO, "Adding fasta: {0}", header);
        fastas.add(new Fasta(header, sequence));
    }
}
