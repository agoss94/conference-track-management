package com.github.agoss94.track.manager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Text input reader reads all Events from a text file and stores the, in a
 * collection.
 */
public class TextInputReader {

    /**
     * Pattern matching only digits.
     */
    private static final Pattern PATTERN_DIGITS = Pattern.compile("\\d+");

    /**
     * Reads the given input file line by line. Empty lines are ignored. We assume
     * that the duration of the event and the title is separated by a single space.
     *
     * @param pathToFile the path to the file.
     * @return a collection of events as read from the file.
     * @throws IOException
     */
    public Collection<Event> readFile(Path pathToFile) throws IOException {
        List<String> lines = Files.readAllLines(pathToFile);
        if (lines.isEmpty()) {
            return Collections.emptyList();
        }
        List<Event> events = new ArrayList<>();
        for (String line : lines) {
            if (!line.isBlank()) {
                Matcher matcher = PATTERN_DIGITS.matcher(line);
                if (matcher.find()) {
                    int min = Integer.parseInt(matcher.group());
                    String title = line.substring(0, matcher.start() - 1);
                    events.add(new Event(title, Duration.ofMinutes(min)));
                }
            }
        }
        return events;
    }

}
