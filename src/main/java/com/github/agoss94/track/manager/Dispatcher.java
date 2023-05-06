package com.github.agoss94.track.manager;

import java.util.Collection;

/**
 * A dispatcher dispatches events to a track.
 */
public interface Dispatcher {

    /**
     * Dispatches the collection of events to a track. We assume that all
     * Events in the collection have a finite duration (no Event is open end).
     *
     * @param events a collection of events.
     * @throws NullPointerException if events is {@code null}.
     */
    Track dispatch(Collection<Event> events);

}
