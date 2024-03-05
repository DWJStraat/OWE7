package org.fasta;

import org.logger.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FastaReader {
    private final Log log = new Log("org.fasta.FastaReader");
    final List<Fasta> fastas = new ArrayList<>();

    public FastaReader(String path) throws IOException {
        init(path);
    }

    public FastaReader (String path, Boolean blast) throws IOException, InterruptedException {
        init(path);
        if (Boolean.TRUE.equals(blast)) {
            blast();
        }
    }

    public void blast () throws InterruptedException {
        for (Fasta fasta : fastas) {
            try {
                fasta.blast();
            } catch (InterruptedException e) {
                log.error("Thread interrupted");
                throw new InterruptedException(e.getMessage());
            }
        }
    }

    private void init (String path) throws IOException {
        log.info("Reading file: " + path);
        File file = new File(path);
        String contents = new String(java.nio.file.Files.readAllBytes(file.toPath()));
        StringBuilder sequence = new StringBuilder();
        String header = "";
        for (String line : contents.split("\n")) {
            if (line.startsWith(">")) {
                log.info("Found header: " + line);
                if (!sequence.isEmpty()) {
                    addFasta(header, sequence.toString());
                    sequence = new StringBuilder();
                }
                header = line;
            } else {
                sequence.append(line);
            }
        }
        addFasta(header, sequence.toString());
    }

    private void addFasta(String header, String sequence) {
        log.info("Adding fasta: " + header);
        fastas.add(new Fasta(header, sequence));
    }
}
