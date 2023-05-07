package com.github.agoss94.track.manager;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConferencePlaner {

    public static void main(String[] args) throws IOException {
        Path pathToFile = Paths.get(args[0]);
        TextInputReader reader = new TextInputReader();
        Collection<Event> events = reader.readFile(pathToFile);

        List<Track> tracks = new ArrayList<>();
        LazyConferenceDispatcher dispatcher = new LazyConferenceDispatcher();
        while (!events.isEmpty()) {
            Track track = dispatcher.dispatch(events);
            events.removeAll(track.values());
            tracks.add(track);
        }

        Path outputFile = pathToFile.resolveSibling("timetable.txt");
        BufferedWriter writer = Files.newBufferedWriter(outputFile, StandardCharsets.UTF_8);
        for (int i = 0; i < tracks.size(); i++) {
            writer.append("Track " + (i + 1) + ":");
            writer.newLine();
            writer.append(tracks.get(i).toString());
            writer.newLine();
        }
        writer.close();
    }
}
