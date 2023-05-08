package com.github.agoss94.track.manager.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.github.agoss94.track.manager.Track;

/**
 * Output writer writes tracks to a text file.
 */
public class OutputWriter {

    /**
     * Writes the different tracks to an output file.
     *
     * @param outputPath the path of the output file.
     * @param tracks     the given tracks
     * @throws IOException if the path is invalid.
     */
    public void writeFile(Path outputPath, List<Track> tracks) throws IOException {
        // Write output
        BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8);
        for (int i = 0; i < tracks.size(); i++) {
            writer.append("Track " + (i + 1) + ":");
            writer.newLine();
            writer.append(tracks.get(i).toString());
            writer.newLine();
        }
        writer.close();
    }
}
