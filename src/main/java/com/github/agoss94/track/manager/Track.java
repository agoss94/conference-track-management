package com.github.agoss94.track.manager;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Map.Entry;
import java.util.Collection;
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
     * @throws NullPointerException   if the start time or event is {@code null}.
     * @throws PreviousEventException if a previous event still blocks the start
     *                                time.
     * @throws FutureEventException   if a future event starts before the given
     *                                event is over.
     */
    public void put(LocalTime start, Event e) {
        Objects.requireNonNull(start);
        Objects.requireNonNull(e);
        if (endPrevious(start).isAfter(start)) {
            throw new PreviousEventException(
                    "Cannot add event to track. There is an ongoing event until " + endPrevious(start));
        }
        LocalTime nextIn = track.higherKey(start);
        if (nextIn != null) {
            if (e.isOpenEnd()) {
                throw new FutureEventException("Cannot add an open end event before a future event.");
            } else if (nextIn.isBefore(start.plus(e.getDuration()))) {
                throw new FutureEventException(
                        String.format("Cannot add event of %smin at %s, because the next event starts at %s",
                                e.getDuration().toMinutes(), start, nextIn));
            }
        }
        track.put(start, e);
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
     * Returns {@code true} if the track is empty.
     *
     * @return {@code true} if the track is empty.
     */
    public boolean isEmpty() {
        return track.isEmpty();
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
     * Returns the time at which the track end.
     *
     * @return the time at which the track end.
     */
    public Duration length() {
        return isEmpty() ? Duration.ZERO : Duration.between(track.firstKey(), end());
    }

    /**
     * Put all events of the given track in this track.
     *
     * @param track the given track.
     * @throws NullPointerException   if track is {@code null}.
     * @throws PreviousEventException if a previous event still blocks the start
     *                                time.
     * @throws FutureEventException   if a future event starts before the given
     *                                event is over.
     */
    public void putAll(Track track) {
        for (Entry<LocalTime, Event> entry : track.track.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Returns the duration of the event.
     *
     * @return the duration of the event.
     */
    public Collection<Event> allEvents() {
        return track.values();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(track);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Track other = (Track) obj;
        return Objects.equals(track, other.track);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Entry<LocalTime, Event> entry : track.entrySet()) {
            sb.append(String.format("%s %s %n", entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }
}
