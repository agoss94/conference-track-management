package com.github.agoss94.track.manager;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntegrationTest {

    private static final Path PATH = Paths.get(System.getProperty("user.dir"), "src", "test", "java", "com",
            "github", "agoss94", "track", "manager", "Events.txt");

    @BeforeEach
    void befor() throws IOException, URISyntaxException {
        ConferencePlaner.main(new String[] { PATH.toAbsolutePath().toString() });
    }

    @Test
    void integrationTest() throws URISyntaxException {
        assertTrue(Files.exists(PATH.resolveSibling("timetable.txt")));
    }

}
