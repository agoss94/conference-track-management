package com.github.agoss94.track.manager.dispatcher;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.github.agoss94.track.manager.Event;
import com.github.agoss94.track.manager.Track;

public class ConferenceDispatcher implements Dispatcher {

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
     */
    @Override
    public Track dispatch(Collection<Event> c) {
        Objects.requireNonNull(c);
        Track track = new Track();
        Set<Event> events = new HashSet<>(c);

        //Afternoon events
        Track afternoonSession = dispatcherAfternoon.dispatch(events);
        track.putAll(afternoonSession);
        LocalTime end = track.end();
        LocalTime networkingStart = end.isBefore(LocalTime.of(16, 0)) ? LocalTime.of(16, 0) : end;
        track.put(networkingStart, new Event("Networking Event"));
        events.removeAll(afternoonSession.values());

        //Morning events
        Track morningsSession = dispatcherMorning.dispatch(events);
        track.putAll(morningsSession);
        track.put(LocalTime.of(12, 0), new Event("Lunch", Duration.ofHours(1)));

        return track;
    }
}
