package org.fasta;

import org.logger.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FastaReader {
    private final Log log = new Log(FastaReader.class.getName());
    private File file;
    final List<Fasta> fastas = new ArrayList<>();

    public FastaReader(String path) throws IOException {
        log.info("Reading file: " + path);
        file = new File(path);
        String contents = new String(java.nio.file.Files.readAllBytes(file.toPath()));
        StringBuilder sequence = new StringBuilder();
        String header = "";
        for (String line : contents.split("\n")) {
            if (line.startsWith(">")) {
                log.info("Found header: " + line);
                if (!sequence.isEmpty()) {
                    fastas.add(new Fasta(header, sequence.toString()));
                    sequence = new StringBuilder();
                }
                header = line;
            } else {
                sequence.append(line);
            }
        }

    }
}
