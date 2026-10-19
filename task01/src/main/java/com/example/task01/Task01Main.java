package com.example.task01;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Task01Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(extractSoundName(new File("src/main/resources/3727.mp3")));
    }

    public static String extractSoundName(File file) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(
                "ffprobe", "-v", "error", "-of", "flat", "-show_format", file.getAbsolutePath()
        );
        Process process = pb.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("format.tags.title=")) {
                    String title = line.substring("format.tags.title=".length());
                    if (title.startsWith("\"") && title.endsWith("\"") && title.length() >= 2) {
                        return title.substring(1, title.length() - 1);
                    }
                    return title;
                }
            }
        } finally {
            process.waitFor();
        }
        return null;
    }
}
