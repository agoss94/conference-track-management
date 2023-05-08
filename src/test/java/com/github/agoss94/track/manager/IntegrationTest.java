package com.github.agoss94.track.manager;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class IntegrationTest extends AbstractTester {

    @BeforeAll
    static void before() throws IOException, URISyntaxException {
        TrackManager.main(new String[] { RESOURCES.resolve("Events.txt").toAbsolutePath().toString() });
    }

    @Test
    void integrationTest() throws URISyntaxException {
        assertTrue(Files.exists(RESOURCES.resolve("timetable.txt")));
    }

}
