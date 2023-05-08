package com.github.agoss94.track.manager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.agoss94.track.manager.dispatcher.Dispatcher;
import com.github.agoss94.track.manager.dispatcher.LazyConferenceDispatcher;
import com.github.agoss94.track.manager.dispatcher.OptimalConferenceDispatcher;
import com.github.agoss94.track.manager.io.InputReader;
import com.github.agoss94.track.manager.io.OutputWriter;

public class TrackManager {

    public static void main(String[] args) throws IOException {
        boolean isOptimalMode = false;
        if(args.length == 2) {
            isOptimalMode = "-optimal".equals(args[1]);
        }

        //Read input
        Path pathToFile = Paths.get(args[0]);
        InputReader reader = new InputReader();
        Collection<Event> events = reader.readFile(pathToFile);

        //Dispatch Events
        List<Track> tracks = new ArrayList<>();
        Dispatcher dispatcher = isOptimalMode ? new OptimalConferenceDispatcher() : new LazyConferenceDispatcher();
        while (!events.isEmpty()) {
            Track track = dispatcher.dispatch(events);
            events.removeAll(track.values());
            tracks.add(track);
        }

        //Write output
        String fileName = pathToFile.getFileName().toString();
        String outputFilename = fileName.replace(".txt", "-timetable.txt");
        OutputWriter writer = new OutputWriter();
        writer.writeFile(pathToFile.resolveSibling(outputFilename), tracks);
    }
}
