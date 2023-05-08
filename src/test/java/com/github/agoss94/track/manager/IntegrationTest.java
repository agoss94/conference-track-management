package com.github.agoss94.track.manager;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class IntegrationTest {

    /**
     * Path for all test resources.
     */
    public static final Path RESOURCES = Paths.get(System.getProperty("user.dir"), "src", "test", "resources");

    @BeforeAll
    static void before() throws IOException, URISyntaxException {
        TrackManager.main(new String[] { RESOURCES.resolve("Conference.txt").toAbsolutePath().toString() });
        TrackManager.main(new String[] { RESOURCES.resolve("Conference2.txt").toAbsolutePath().toString(), "-optimal" });
        TrackManager.main(new String[] { RESOURCES.resolve("Conference3.txt").toAbsolutePath().toString()});
    }

    @Test
    void integrationTest() throws URISyntaxException {
        assertTrue(Files.exists(RESOURCES.resolve("Conference-timetable.txt")));
    }

    @Test
    void integrationTestOptimalMode() throws URISyntaxException {
        assertTrue(Files.exists(RESOURCES.resolve("Conference2-timetable.txt")));
    }

    @Test
    void integrationTestMoreEvents() throws URISyntaxException {
        assertTrue(Files.exists(RESOURCES.resolve("Conference3-timetable.txt")));
    }

}
