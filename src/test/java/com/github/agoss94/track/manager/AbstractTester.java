package com.github.agoss94.track.manager;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AbstractTester {

    /**
     * Path for all test resources.
     */
    public static final Path RESOURCES = Paths.get(System.getProperty("user.dir"), "src", "test", "resources");

}
