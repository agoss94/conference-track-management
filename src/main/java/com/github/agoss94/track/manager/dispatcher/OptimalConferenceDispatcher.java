package com.github.agoss94.track.manager.dispatcher;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.github.agoss94.track.manager.Event;
import com.github.agoss94.track.manager.Track;

/**
 * An implementation of an optimal conference dispatcher based on the algorithm
 * described in {@link OptimalDispatcher}.
 */
public class OptimalConferenceDispatcher implements Dispatcher {

    /**
     * Dispatcher for finding a optimal morning session.
     */
    private final Dispatcher dispatcherMorning = new OptimalDispatcher(LocalTime.of(9, 0), Duration.ofHours(3));

    /**
     * Dispatcher for finding a optimal afternoon session.
     */
    private final Dispatcher dispatcherAfternoon = new OptimalDispatcher(LocalTime.of(13, 0), Duration.ofHours(4));

    /**
     * {@inheritDoc}
     *
     * @throws NullPointerException     if the given collection is {@code null}.
     * @throws IllegalArgumentException if any of the Events is longer than 4 hours.
     */
    @Override
    public Track dispatch(Collection<Event> c) {
        Objects.requireNonNull(c);
        if (c.stream().anyMatch(e -> isEventToLong(e))) {
            throw new IllegalArgumentException("One of the events is longer than 4 hours!");
        }
        Track track = new Track();
        Set<Event> events = new HashSet<>(c);

        // Morning events
        Track morningsSession = dispatcherMorning.dispatch(events);
        track.putAll(morningsSession);
        track.put(LocalTime.of(12, 0), new Event("Lunch", Duration.ofHours(1)));
        events.removeAll(morningsSession.values());

        // Afternoon events
        Track afternoonSession = dispatcherAfternoon.dispatch(events);
        track.putAll(afternoonSession);
        LocalTime end = track.end();
        LocalTime networkingStart = end.isBefore(LocalTime.of(16, 0)) ? LocalTime.of(16, 0) : end;
        track.put(networkingStart, new Event("Networking Event"));

        return track;
    }

    /**
     * Returns {@code true} if the end is open end or longer than 5 hours.
     *
     * @param e the given event.
     * @return {@code true} if the end is open end or longer than 5 hours.
     */
    private boolean isEventToLong(Event e) {
        return e.isOpenEnd() || e.getDuration().compareTo(Duration.ofHours(4)) > 0;
    }
}
