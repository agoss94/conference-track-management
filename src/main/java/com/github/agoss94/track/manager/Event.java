package com.github.agoss94.track.manager;

import java.time.Duration;

/**
 * This class represent an event. A event has a title and a duration.
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
     */
    public Event(String title, Duration duration) {
        this.title = title;
        this.duration = duration;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the duration
     */
    public Duration getDuration() {
        return duration;
    }

}
