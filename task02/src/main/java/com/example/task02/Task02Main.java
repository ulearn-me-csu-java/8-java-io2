
package com.example.task02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class Task02Main {

    public static void main(String[] args)
            throws IOException, InterruptedException {

        System.out.println(
                listFiles(Path.of("task02/src/main/resources/"))
        );
    }

    public static List<Path> listFiles(Path rootDir)
            throws IOException, InterruptedException {

        try (Stream<Path> paths = Files.walk(rootDir)) {
            return paths
                    .filter(Files::isRegularFile)
                    .toList();
        }
    }
}
