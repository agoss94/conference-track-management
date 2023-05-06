package com.github.agoss94.track.manager;

/**
 * Exception indicates, that an event cannot be added to a track, because it is
 * in conflict with a previous event.
 */
@SuppressWarnings("serial")
public class PreviousEventException extends IllegalStateException {

    /**
     * Creates a new PreviousEventException with the given message.
     *
     * @param message the message of the exception.
     */
    public PreviousEventException(String message) {
        super(message);
    }

}
