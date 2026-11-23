package com.example.task02;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Task02Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println(listFiles(Paths.get("task02/src/main/resources/")));


    }


    public static List<Path> listFiles(Path rootDir) throws IOException, InterruptedException {
        List<Path> result = new ArrayList<>();
        try (var paths = Files.list(rootDir)) {
            for (Path path : paths.toList()) {
                if (Files.isDirectory(path)) {
                    result.addAll(listFiles(path));
                } else {
                    result.add(path);
                }
            }
        }
        return result;
    }
}
