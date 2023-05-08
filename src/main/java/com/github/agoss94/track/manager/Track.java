package com.github.agoss94.track.manager;

import java.time.LocalTime;
import java.util.AbstractMap;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * A track is a timetable of non-overlapping events. As such a track maps points
 * in time to events.
 */
public class Track extends AbstractMap<LocalTime, Event> {

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
     * Associates the start time with the given event. The method always returns
     * {@code null} in order to adhere to the {@link Map} interface. It is not
     * possible to add an event to the map, which overlaps with a previous or later
     * event in which case an exception is thrown.
     *
     * @param start the start time.
     * @param e     the event.
     * @return {@code null}
     * @throws NullPointerException  if the start time or event is {@code null}.
     * @throws IllegalStateException if a previous event still blocks the start time
     *                               or if a future event starts before the given
     *                               event is over.
     */
    @Override
    public Event put(LocalTime start, Event e) {
        Objects.requireNonNull(start);
        Objects.requireNonNull(e);
        if (endPrevious(start).isAfter(start)) {
            throw new IllegalStateException(
                    "Cannot add event to track. There is an ongoing event until " + endPrevious(start));
        }
        LocalTime nextIn = track.higherKey(start);
        if (nextIn != null) {
            if (e.isOpenEnd()) {
                throw new IllegalStateException("Cannot add an open end event before a future event.");
            } else if (nextIn.isBefore(start.plus(e.getDuration()))) {
                throw new IllegalStateException(
                        String.format("Cannot add event of %smin at %s, because the next event starts at %s",
                                e.getDuration().toMinutes(), start, nextIn));
            }
        }
        return track.put(start, e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Entry<LocalTime, Event>> entrySet() {
        return track.entrySet();
    }

    /**
     * Forwarding {@link NavigableMap#ceilingKey(Object)}
     */
    public LocalTime ceilingKey(LocalTime time) {
        return track.ceilingKey(time);
    }

    /**
     * Returns the end time for the event, which started equal to or directly before
     * the given time. If the previous event is open end {@link LocalTime#MAX} is
     * returned. If the track is empty {@link LocalTime#MIN} is returned.
     *
     * @param time the given time.
     * @return the end time the previous event or {@link LocalTime#MIN} if the track
     *         is empty or {@link LocalTime#MAX} if the last event is open end.
     * @throws NullPointerException if time is {@code null}.
     */
    public LocalTime endPrevious(LocalTime time) {
        Objects.requireNonNull(time);
        Entry<LocalTime, Event> entry = track.floorEntry(time);
        if (entry != null) {
            LocalTime start = entry.getKey();
            Event event = entry.getValue();
            return event.isOpenEnd() ? LocalTime.MAX : start.plus(event.getDuration());
        } else {
            return LocalTime.MIN;
        }
    }

    /**
     * Returns the time at which the track end.
     *
     * @return the time at which the track end.
     */
    public LocalTime end() {
        Entry<LocalTime, Event> last = track.lastEntry();
        if (last != null) {
            LocalTime start = last.getKey();
            Event e = last.getValue();
            return e.isOpenEnd() ? LocalTime.MAX : start.plus(e.getDuration());
        } else {
            return LocalTime.MIN;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Entry<LocalTime, Event> entry : track.entrySet()) {
            sb.append(String.format("%s %s %n", entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }

}
