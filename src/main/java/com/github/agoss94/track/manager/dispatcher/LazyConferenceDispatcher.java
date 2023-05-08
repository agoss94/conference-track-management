package com.github.agoss94.track.manager.dispatcher;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
     */
    @Override
    public Track dispatch(Collection<Event> c) {
        events = new ArrayList<>(c);
        // Sorting ensures that the longest events are dispatched first.
        events.sort(Event.COMPARATOR.reversed());

        // Fill in fixed Events.
        track = new Track();
        track.put(LocalTime.of(12, 0), new Event("Lunch", Duration.ofHours(1)));
        track.put(LocalTime.of(17, 0), new Event("Networking Event"));

        time = LocalTime.of(9, 0);
        while (time.isBefore(LocalTime.MAX)) {
            dispatchEvent();
        }

        return track;
    }

    /**
     * Dispatch a event for the next free slot.
     */
    private void dispatchEvent() {
        // Ceiling key because the end of the last event can also be the beginning of
        // the next.
        LocalTime nextEvent = track.ceilingKey(time);
        Duration timeUntilNext = Duration.between(time, nextEvent);
        Optional<Event> event = events.stream().filter(e -> e.getDuration().compareTo(timeUntilNext) <= 0).findFirst();
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
}
