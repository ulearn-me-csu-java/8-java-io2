package com.example.task02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Task02Main {
    public static void main(String[] args) throws IOException, InterruptedException {
    }

    public static List<Path> listFiles(Path rootDir) throws IOException, InterruptedException {
        try (Stream<Path> stream = Files.walk(rootDir)) {
            return stream.filter(Files::isRegularFile).collect(Collectors.toList());
        }
    }
}
