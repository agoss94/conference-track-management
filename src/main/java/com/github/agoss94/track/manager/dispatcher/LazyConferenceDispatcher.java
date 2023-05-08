package com.github.agoss94.track.manager.dispatcher;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.github.agoss94.track.manager.Event;
import com.github.agoss94.track.manager.Track;

/**
 * A lazy implementation of a dispatcher, which tries to add Events as long as
 * possible.
 */
public class LazyConferenceDispatcher implements Dispatcher {

    /**
     * The track to which events are dispatched.
     */
    private Track track;

    /**
     * The list of Events
     */
    private List<Event> events;

    /**
     * Time counter
     */
    private LocalTime time;

    /**
     * {@inheritDoc}
     *
     * @throws NullPointerException     if the given collection is {@code null}.
     * @throws IllegalArgumentException if any of the Events is longer than 4
     *                                  hoursor open end.
     */
    @Override
    public Track dispatch(Collection<Event> c) {
        Objects.requireNonNull(c);
        if (c.stream().anyMatch(e -> isEventToLong(e))) {
            throw new IllegalArgumentException("One of the events is longer than 4 hours!");
        }

        // Fill in fixed Events.
        track = new Track();
        track.put(LocalTime.of(12, 0), new Event("Lunch", Duration.ofHours(1)));
        track.put(LocalTime.of(17, 0), new Event("Networking Event"));

        // Dispatch the rest for as long as possible.
        time = LocalTime.of(9, 0);
        events = new ArrayList<>(c);
        while (time.isBefore(LocalTime.MAX)) {
            dispatchEvent();
        }

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

    /**
     * Dispatch a event for the next free slot.
     */
    private void dispatchEvent() {
        // Ceiling key because the end of the last event can also be the beginning of
        // the next.
        LocalTime nextEvent = track.ceilingKey(time);
        Duration timeUntilNext = Duration.between(time, nextEvent);
        Optional<Event> event = events.stream().filter(e -> fitsInBetween(timeUntilNext, e)).findFirst();
        if (event.isPresent()) {
            Event e = event.get();
            track.put(time, e);
            events.remove(e);
            // Since the event has been added the end previous jumps to the end of the last
            // added event.
            time = track.endPrevious(time);
        } else {
            // Since no Event fits into the time slot between the given time and the next
            // event. We jump to the end of the next.
            time = track.endPrevious(nextEvent);
        }
    }

    /**
     * Return {@code true} if the duration of the event is shorter than the given
     * duration.
     *
     * @param timeUntilNext the time until the next event.
     * @param e the given event.
     * @return {@code true} if the duration of the event is shorter than the given
     * duration.
     */
    private boolean fitsInBetween(Duration timeUntilNext, Event e) {
        return e.getDuration().compareTo(timeUntilNext) <= 0;
    }
}
