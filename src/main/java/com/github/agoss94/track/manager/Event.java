package com.github.agoss94.track.manager;

import java.time.Duration;
import java.util.Objects;

/**
 * This class represent an event. A event has a title and a duration. The
 * duration may be {@code null} if the event is open end.
 */
public class Event {

    /**
     * The title of the event.
     */
    private final String title;

    /**
     * The duration of the talk in minutes.
     */
    private final Duration duration;

    /**
     * Simple constructor creates a Talk object with the given title and specified
     * duration.
     *
     * @param title    the title of the talk.
     * @param duration the duration of the talk. The duration may be {@code null},
     *                 which signals that the event is open ended.
     * @throws NullPointerException if the title is {@code null}.
     */
    public Event(String title, Duration duration) {
        this.title = Objects.requireNonNull(title);
        this.duration = duration;
    }

    /**
     * Simple constructor creates a Talk object with the given title and specified
     * duration.
     *
     * @param title    the title of the talk.
     * @param duration the duration of the talk. The duration may be {@code null},
     *                 which signals that the event is open ended.
     */
    public Event(String title) {
        this(title, null);
    }

    /**
     * Returns the title title of the event.
     *
     * @return the title title of the event.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the duration of the event.
     *
     * @return the duration of the event.
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * Returns {@code true} if the event is open end.
     *
     * @return {@code true} if the event is open end.
     */
    public boolean isOpenEnd() {
        return duration == null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(duration, title);
    }

    @Override
    public String toString() {
        return isOpenEnd() ? title : String.format("%s %smin", title, duration.toMinutes());
    }
}
