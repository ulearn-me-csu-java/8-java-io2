package com.example.task01;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Task01Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        //здесь вы можете вручную протестировать ваше решение, вызывая реализуемый метод и смотря результат
        // например вот так:

        /*
        System.out.println(extractSoundName(new File("task01/src/main/resources/3727.mp3")));
        */

        // temporary for testing & debug
        var temp = extractSoundName(new File("C:\\Users\\Lenovo\\IdeaProjects\\8-java-io2\\task01\\src\\main\\resources\\3724.mp3"));
        System.out.println(temp);
    }

    public static String extractSoundName(File file) throws IOException, InterruptedException {
        String title = null;
        var processBuilder = new ProcessBuilder(
                "ffprobe",
                "-v",
                "error",
                "-of",
                "flat",
                "-show_format",
                file.toString()
        );

        //processBuilder.inheritIO(); temporary for debugging


        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("format.tags.title")) {
                    title = line.substring(19, line.length() - 1); // using indexes to get rid of ""
                }
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("ffprobe exited with code " + exitCode);
        }
        if (title == null) {
            throw new IllegalArgumentException("The mp3 track title couldn't be found");
        }
        return title;
    }
}
