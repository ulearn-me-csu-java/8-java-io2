package com.example.task01;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Task01Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(extractSoundName(new File("C:\\Users\\Deoksisss\\Desktop\\file.mp3")));
    }

    public static String extractSoundName(File file) throws IOException, InterruptedException {
        var builder = new ProcessBuilder("ffprobe", "-v", "error", "-of", "flat", "-show_format", file.getAbsolutePath());
        builder.redirectErrorStream(true);
        String prefix = "format.tags.title=";
        String title = null;

        Process probe = builder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(probe.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.regionMatches(true, 0, prefix, 0, prefix.length())) {
                    String value = line.substring(prefix.length());
                    if (value.length() >= 2 && value.startsWith("\"") && value.endsWith("\"")) {
                        value = value.substring(1, value.length() - 1);
                    }
                    title = value;
                }
            }
        }
        probe.waitFor();
        return title;
    }
}
