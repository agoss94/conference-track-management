package com.github.agoss94.track.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrackTest {

    private Track track;

    @BeforeEach
    void setup() {
        track = new Track();
    }

    @Test
    void putThrowsExceptionIfStartIsNull() {
        assertThrows(NullPointerException.class, () -> track.put(null, new Event("Talk1")));
    }

    @Test
    void putThrowsExceptionIfEventIsNull() {
        assertThrows(NullPointerException.class, () -> track.put(LocalTime.of(9, 0), null));
    }

    @Test
    void putReturnsNullIfNoPreviousMappingWasPresent() {
        assertNull(track.put(LocalTime.of(9, 0), new Event("Talk1")));
    }

    @Test
    void putReturnsPreviousMappingIfPresent() {
        Event e1 = new Event("Talk1");
        Event e2 = new Event("Talk2");
        assertNull(track.put(LocalTime.of(9, 0), e1));
        assertEquals(track.put(LocalTime.of(9, 0), e2), e1);
    }

    @Test
    void ongoingUntilThrowsNullpointer() {
        assertThrows(NullPointerException.class, () -> track.put(LocalTime.of(9, 0), null));
    }

}
