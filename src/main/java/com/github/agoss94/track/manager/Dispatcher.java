package com.github.agoss94.track.manager;

import java.util.Collection;

/**
 * A dispatcher dispatches events to a track.
 */
public interface Dispatcher {

    /**
     * Dispatches the collection of events to the given track.
     *
     * @param track  a given track.
     * @param events a collection of events.
     */
    void dispatch(Track track, Collection<Event> events);

}
