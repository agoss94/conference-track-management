package com.github.agoss94.track.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.agoss94.track.manager.dispatcher.Dispatcher;
import com.github.agoss94.track.manager.dispatcher.OptimalDispatcher;

public class OptimalDispatcherTest {

    private Dispatcher dispatcher;

    @BeforeEach
    void setup() {
        dispatcher = new OptimalDispatcher(LocalTime.of(9, 0), Duration.ofHours(3));
    }

    @Test
    void throwsNullpointerIfEventsIsNull() {
        assertThrows(NullPointerException.class, () -> dispatcher.dispatch(null));
    }

    @Test
    void emptyCollectionReturnsthrowsException() {
        assertThrows(IllegalArgumentException.class, () -> dispatcher.dispatch(Collections.emptySet()));
    }

    @Test
    void eventSetWithoutSolutionThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> dispatcher.dispatch(Set.of(new Event("Talk1", Duration.ofHours(4)))));
    }

    @Test
    void allEventsIncluded() {
        Event talk1 = new Event("Talk1", Duration.ofHours(1));
        Event talk2 = new Event("Talk2", Duration.ofHours(1));
        Event talk3 = new Event("Talk3", Duration.ofHours(1));
        List<Event> events = List.of(talk1, talk2, talk3);

        // Expected solution
        Track expected = new Track();
        expected.put(LocalTime.of(9, 0), talk1);
        expected.put(LocalTime.of(10, 0), talk2);
        expected.put(LocalTime.of(11, 0), talk3);
        assertEquals(expected, dispatcher.dispatch(events));
    }

    @Test
    void onlySubsetIsSolution() {
        Event talk1 = new Event("Talk1", Duration.ofHours(3));
        Event talk2 = new Event("Talk2", Duration.ofHours(1));
        Event talk3 = new Event("Talk3", Duration.ofHours(1));
        List<Event> events = List.of(talk1, talk2, talk3);

        // Expected solution
        Track expected = new Track();
        expected.put(LocalTime.of(9, 0), talk1);
        assertEquals(expected, dispatcher.dispatch(events));
    }

    @Test
    void noExactSolution() {
        Event talk1 = new Event("Talk1", Duration.ofHours(2));
        Event talk2 = new Event("Talk2", Duration.ofMinutes(45));
        Event talk3 = new Event("Talk3", Duration.ofMinutes(90));
        List<Event> events = List.of(talk1, talk2, talk3);

        // Expected solution
        Track expected = new Track();
        expected.put(LocalTime.of(9, 0), talk1);
        expected.put(LocalTime.of(11, 0), talk2);
        assertEquals(expected, dispatcher.dispatch(events));
    }

    @Test
    void multiplePossibleSolutions() {
        Event talk1 = new Event("Talk1", Duration.ofHours(2));
        Event talk2 = new Event("Talk2", Duration.ofMinutes(45));
        Event talk3 = new Event("Talk3", Duration.ofMinutes(90));
        Event talk4 = new Event("Talk4", Duration.ofMinutes(45));
        Event talk5 = new Event("Talk5", Duration.ofMinutes(60));
        Event talk6 = new Event("Talk6", Duration.ofMinutes(15));
        Event talk7 = new Event("Talk7", Duration.ofMinutes(30));
        Event talk8 = new Event("Talk8", Duration.ofMinutes(75));
        Event talk9 = new Event("Talk9", Duration.ofMinutes(60));
        Event talk10 = new Event("Talk10", Duration.ofMinutes(60));
        List<Event> events = List.of(talk1, talk2, talk3, talk4, talk5, talk6, talk7, talk8, talk9, talk10);

        Track optimalTrack = dispatcher.dispatch(events);
        assertEquals(LocalTime.of(12, 0), optimalTrack.end());
    }
}
