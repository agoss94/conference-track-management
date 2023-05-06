package com.github.agoss94.track.manager;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TrackTest {

    private Track track;

    @Test
    void putThrowsExceptionIfStartIsNull() {
        assertThrows(NullPointerException.class, () -> track.put(null, new Event("Talk1")));
    }

    @Test
    void putThrowsExceptionIfEventIsNull() {
        assertThrows(NullPointerException.class, () -> track.put(LocalTime.of(9, 0), null));
    }

}
