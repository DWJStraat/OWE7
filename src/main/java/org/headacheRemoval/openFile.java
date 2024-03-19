package org.headacheRemoval;

import org.fasta.FastaReader;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * A class that allows for the opening of a file through a GUI.
 */
public class openFile {
    public File file;
    public List<String> content;
    public String name;
    public String path;
    public FastaReader reader;

    /**
     * Initializes the file chooser and opens a file.
     */
    public openFile() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setDialogTitle("Select a file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            path = file.getAbsolutePath();
            name = file.getName();
            readFile();
        } else {
            throw new IOException("No File Selected");
        }
    }

    /**
     * Reads the contents of the file.
     */
    public void readFile() {

        try {
            reader = new FastaReader(file.toPath().toString());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
