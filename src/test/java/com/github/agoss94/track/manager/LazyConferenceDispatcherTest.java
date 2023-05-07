package com.github.agoss94.track.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;

public class LazyConferenceDispatcherTest {

    @Test
    void conferenceDispatcherTest1() {
        List<Event> events =List.of(
                                new Event("Writing Fast Tests Against Enterprise Rails", Duration.ofMinutes(60)),
                                new Event("Overdoing it in Python", Duration.ofMinutes(45)),
                                new Event("Lua for the Masses", Duration.ofMinutes(30)),
                                new Event("Ruby Errors from Mismatched Gem Versions", Duration.ofMinutes(45)),
                                new Event("Common Ruby Errors", Duration.ofMinutes(45)),
                                new Event("Rails for Python Developers", Duration.ofMinutes(5)),
                                new Event("Communicating Over Distance", Duration.ofMinutes(60)),
                                new Event("Accounting-Driven Development", Duration.ofMinutes(45)),
                                new Event("Woah", Duration.ofMinutes(30)),
                                new Event("Sit Down and Write", Duration.ofMinutes(30)),
                                new Event("Pair Programming vs Noise", Duration.ofMinutes(45)),
                                new Event("Rails Magic", Duration.ofMinutes(60)),
                                new Event("Ruby on Rails", Duration.ofMinutes(60)),
                                new Event("Clojure Ate Scala (on my project)", Duration.ofMinutes(45)),
                                new Event("Programming in the Boondocks of Seattle", Duration.ofMinutes(30)),
                                new Event("Ruby vs. Clojure for Back-End Development", Duration.ofMinutes(30)),
                                new Event("Ruby on Rails Legacy App Maintenance", Duration.ofMinutes(60)),
                                new Event("A World Without HackerNews", Duration.ofMinutes(30)),
                                new Event("User Interface CSS in Rails Apps", Duration.ofMinutes(30))
                            );
        Dispatcher dispatcher = new LazyConferenceDispatcher();
        Track track =  dispatcher.dispatch(events);
        Event talk1 = track.get(LocalTime.of(9, 0));
        Event talk2 = track.get(LocalTime.of(10, 0));
        Event talk3 = track.get(LocalTime.of(11, 0));
        Event lunch = track.get(LocalTime.of(12, 0));
        Event talk4 = track.get(LocalTime.of(13, 0));
        Event talk5 = track.get(LocalTime.of(14, 0));
        Event talk6 = track.get(LocalTime.of(15, 0));
        Event talk7 = track.get(LocalTime.of(15, 45));
        Event talk8 = track.get(LocalTime.of(16, 30));
        Event networking = track.get(LocalTime.of(17, 0));
        assertEquals("Writing Fast Tests Against Enterprise Rails", talk1.getTitle());
        assertEquals("Communicating Over Distance", talk2.getTitle());
        assertEquals("Rails Magic", talk3.getTitle());
        assertEquals("Lunch", lunch.getTitle());
        assertEquals("Ruby on Rails", talk4.getTitle());
        assertEquals("Ruby on Rails Legacy App Maintenance", talk5.getTitle());
        assertEquals("Overdoing it in Python", talk6.getTitle());
        assertEquals("Ruby Errors from Mismatched Gem Versions", talk7.getTitle());
        assertEquals("Lua for the Masses", talk8.getTitle());
        assertEquals("Networking Event", networking.getTitle());
    }

}
