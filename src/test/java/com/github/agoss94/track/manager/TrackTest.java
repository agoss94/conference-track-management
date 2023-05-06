package com.github.agoss94.track.manager;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrackTest {

    private static final Event TALK_ONE_HOUR = new Event("Talk1", Duration.ofHours(1));

    private static final Event TALK_OPEN_END = new Event("Talk1");

    private Track track;

    @BeforeEach
    void setup() {
        track = new Track();
    }

    @Test
    void putThrowsExceptionIfStartIsNull() {
        assertThrows(NullPointerException.class, () -> track.put(null, TALK_ONE_HOUR));
    }

    @Test
    void putThrowsExceptionIfEventIsNull() {
        assertThrows(NullPointerException.class, () -> track.put(LocalTime.of(9, 0), null));
    }

    @Test
    void putReturnsThrowsNoException() {
        assertDoesNotThrow(() -> track.put(LocalTime.of(9, 0), TALK_ONE_HOUR));
    }

    @Test
    void putThrowsPreviousEventException() {
        assertDoesNotThrow(() -> track.put(LocalTime.of(9, 0), TALK_ONE_HOUR));
        // cannot add talk at same time.
        PreviousEventException e = assertThrows(PreviousEventException.class,
                () -> track.put(LocalTime.of(9, 0), TALK_OPEN_END));
        assertEquals("Cannot add event to track. There is an ongoing event until 10:00", e.getMessage());
        // Talk can be added at the end.
        assertDoesNotThrow(() -> track.put(LocalTime.of(10, 0), TALK_OPEN_END));
    }

    @Test
    void putThrowsFutureEventExceptionForOpenEndEventBeforeOthers() {
        assertDoesNotThrow(() -> track.put(LocalTime.of(11, 0), TALK_ONE_HOUR));
        // cannot add talk at same time.
        FutureEventException e = assertThrows(FutureEventException.class,
                () -> track.put(LocalTime.of(9, 0), TALK_OPEN_END));
        assertEquals("Cannot add an open end event before a future event.", e.getMessage());
        // Talk can be added at the end.
    }

    @Test
    void putThrowsFutureEventExceptionForEventsInConflict() {
        assertDoesNotThrow(() -> track.put(LocalTime.of(11, 0), TALK_ONE_HOUR));
        // cannot add talk at same time.
        FutureEventException e = assertThrows(FutureEventException.class,
                () -> track.put(LocalTime.of(10, 30), TALK_ONE_HOUR));
        assertEquals("Cannot add event of 60min at 10:30, because the next event starts at 11:00", e.getMessage());
        // Talk can be added at the end.
    }

    @Test
    void ongoingUntilThrowsNullpointer() {
        assertThrows(NullPointerException.class, () -> track.put(LocalTime.of(9, 0), null));
    }

    @Test
    void ongoingUntilReturnsLocalTimeMINForEmptyTracks() {
        LocalTime nine = LocalTime.of(9, 0);
        assertEquals(LocalTime.MIN, track.endPrevious(nine));
    }

    @Test
    void ongoingUntilForOngoingEvent() {
        track.put(LocalTime.of(9, 0), TALK_ONE_HOUR);
        LocalTime nine = LocalTime.of(9, 0);
        assertEquals(LocalTime.of(10, 0), track.endPrevious(nine));
    }

    @Test
    void ongoingUntilOpenEndEvent() {
        track.put(LocalTime.of(9, 0), TALK_OPEN_END);
        LocalTime nine = LocalTime.of(9, 0);
        assertEquals(LocalTime.MAX, track.endPrevious(nine));
    }

    @Test
    void ongoingUntilForPreviouslyEndedEvent() {
        track.put(LocalTime.of(9, 0), TALK_ONE_HOUR);
        LocalTime eleven = LocalTime.of(11, 0);
        assertEquals(LocalTime.of(10, 0), track.endPrevious(eleven));
    }
}
