package com.github.agoss94.track.manager;

import java.time.LocalTime;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.TreeMap;

/**
 * A track is a timetable of non-overlapping events. As such a track maps points
 * in time to events.
 */
public class Track {

    /**
     * The timetable is represented by a map.
     */
    private final NavigableMap<LocalTime, Event> track;

    /**
     * Creates an empty track.
     */
    public Track() {
        track = new TreeMap<>();
    }

    /**
     * Associates the start time with the given event. Returns the previous mapping
     * for the key or {@code null} if none was present.
     *
     * @param start the start time.
     * @param e     the event.
     * @return the previously associated event.
     * @throws NullPointerException if the start time or event is {@code null}.
     */
    public Event put(LocalTime start, Event e) {
        Objects.requireNonNull(start);
        Objects.requireNonNull(e);
        return track.put(start, e);
    }

}
