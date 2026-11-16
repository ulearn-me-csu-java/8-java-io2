package com.example.task01;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Task01Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(extractSoundName(new File("task01/src/main/resources/3727.mp3")));
    }

    public static String extractSoundName(File file) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(
                "ffprobe",
                "-v", "error",
                "-of", "flat",
                "-show_format",
                file.getAbsolutePath()
        );

        Process process = pb.start();
        String title = null;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            final String prefix = "format.tags.title=";
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(prefix)) {
                    String value = line.substring(prefix.length());

                    if (value.length() >= 2 && value.startsWith("\"") && value.endsWith("\"")) {
                        value = value.substring(1, value.length() - 1);
                    }
                    value = value.replace("\\\"", "\"").replace("\\\\", "\\");

                    title = value;
                    break;
                }
            }
        }
        return title;
    }
}
