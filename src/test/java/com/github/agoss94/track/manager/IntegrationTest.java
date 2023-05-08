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
        TrackManager.main(new String[] { RESOURCES.resolve("Conference.txt").toAbsolutePath().toString() });
        TrackManager.main(new String[] { RESOURCES.resolve("Conference2.txt").toAbsolutePath().toString(), "-optimal" });
    }

    @Test
    void integrationTest() throws URISyntaxException {
        assertTrue(Files.exists(RESOURCES.resolve("Conference-timetable.txt")));
    }

    @Test
    void integrationTestOptimalMode() throws URISyntaxException {
        assertTrue(Files.exists(RESOURCES.resolve("Conference2-timetable.txt")));
    }

}
