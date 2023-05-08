package com.github.agoss94.track.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.github.agoss94.track.manager.dispatcher.Dispatcher;
import com.github.agoss94.track.manager.dispatcher.LazyConferenceDispatcher;

public class LazyConferenceDispatcherTest {

    @Test
    void throwsNullPointerForNullInput() {
        Dispatcher dispatcher = new LazyConferenceDispatcher();
        assertThrows(NullPointerException.class, () -> dispatcher.dispatch(null));
    }

    @Test
    void throwsIllegalArgumentExceptionForToLongEvents() {
        Dispatcher dispatcher = new LazyConferenceDispatcher();
        assertThrows(IllegalArgumentException.class,
                () -> dispatcher.dispatch(Set.of(new Event("Talk", Duration.ofHours(5)))));
    }

    @Test
    void throwsIllegalArgumentExceptionForOpenEndEvents() {
        Dispatcher dispatcher = new LazyConferenceDispatcher();
        assertThrows(IllegalArgumentException.class,
                () -> dispatcher.dispatch(Set.of(new Event("Talk"))));
    }

    @Test
    void conferenceDispatcherTest1() {
        List<Event> events = List.of(new Event("Writing Fast Tests Against Enterprise Rails", Duration.ofMinutes(60)),
                new Event("Overdoing it in Python", Duration.ofMinutes(45)),
                new Event("Lua for the Masses", Duration.ofMinutes(30)),
                new Event("Ruby Errors from Mismatched Gem Versions", Duration.ofMinutes(45)),
                new Event("Common Ruby Errors", Duration.ofMinutes(45)),
                new Event("Rails for Python Developers", Duration.ofMinutes(5)),
                new Event("Communicating Over Distance", Duration.ofMinutes(60)),
                new Event("Accounting-Driven Development", Duration.ofMinutes(45)),
                new Event("Woah", Duration.ofMinutes(30)), new Event("Sit Down and Write", Duration.ofMinutes(30)),
                new Event("Pair Programming vs Noise", Duration.ofMinutes(45)),
                new Event("Rails Magic", Duration.ofMinutes(60)), new Event("Ruby on Rails", Duration.ofMinutes(60)),
                new Event("Clojure Ate Scala (on my project)", Duration.ofMinutes(45)),
                new Event("Programming in the Boondocks of Seattle", Duration.ofMinutes(30)),
                new Event("Ruby vs. Clojure for Back-End Development", Duration.ofMinutes(30)),
                new Event("Ruby on Rails Legacy App Maintenance", Duration.ofMinutes(60)),
                new Event("A World Without HackerNews", Duration.ofMinutes(30)),
                new Event("User Interface CSS in Rails Apps", Duration.ofMinutes(30)));
        Dispatcher dispatcher = new LazyConferenceDispatcher();
        Track track = dispatcher.dispatch(events);
        Event talk1 = track.get(LocalTime.of(9, 0));
        Event talk2 = track.get(LocalTime.of(10, 0));
        Event talk3 = track.get(LocalTime.of(10, 45));
        Event lunch = track.get(LocalTime.of(12, 0));
        Event talk4 = track.get(LocalTime.of(13, 0));
        Event talk5 = track.get(LocalTime.of(13, 45));
        Event talk6 = track.get(LocalTime.of(13, 50));
        Event talk7 = track.get(LocalTime.of(14, 50));
        Event talk8 = track.get(LocalTime.of(15, 35));
        Event talk9 = track.get(LocalTime.of(16, 05));
        Event networking = track.get(LocalTime.of(17, 0));
        assertEquals("Writing Fast Tests Against Enterprise Rails", talk1.getTitle());
        assertEquals("Overdoing it in Python", talk2.getTitle());
        assertEquals("Lua for the Masses", talk3.getTitle());
        assertEquals("Lunch", lunch.getTitle());
        assertEquals("Common Ruby Errors", talk4.getTitle());
        assertEquals("Rails for Python Developers", talk5.getTitle());
        assertEquals("Communicating Over Distance", talk6.getTitle());
        assertEquals("Accounting-Driven Development", talk7.getTitle());
        assertEquals("Woah", talk8.getTitle());
        assertEquals("Sit Down and Write", talk9.getTitle());
        assertEquals("Networking Event", networking.getTitle());
    }

}
