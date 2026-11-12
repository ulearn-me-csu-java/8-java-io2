package com.example.task02;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class Task02Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        //здесь вы можете вручную протестировать ваше решение, вызывая реализуемый метод и смотря результат
        // например вот так:

        System.out.println(listFiles(Paths.get("task02/src/main/resources/")));
    }

    public static List<Path> listFiles(Path rootDir) throws IOException, InterruptedException {
        if (!Files.exists(rootDir) || !Files.isDirectory(rootDir)) throw new IOException();

        List<Path> files = new ArrayList<>();

        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(rootDir)) {
            for (Path path : dirStream) {
                if (Files.isDirectory(path)) {
                    files.addAll(listFiles(path));
                } else {
                    files.add(path);
                }
            }
        }

        return files;
    }
}
