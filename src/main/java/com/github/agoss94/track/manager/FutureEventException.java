package com.github.agoss94.track.manager;

/**
 * Exception indicates, that an event cannot be added to a track because it is
 * in conflict with an future event.
 */
@SuppressWarnings("serial")
public class FutureEventException extends IllegalStateException {

    /**
     * Creates a new FutureEventException with the given message.
     *
     * @param message the message of the exception.
     */
    public FutureEventException(String message) {
        super(message);
    }
}
