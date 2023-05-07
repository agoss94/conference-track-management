package com.github.agoss94.track.manager;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ConferenceDispatcherTest {

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
        Dispatcher dispatcher = new ConferenceDispatcher();
        System.out.println(dispatcher.dispatch(events));
    }

}
