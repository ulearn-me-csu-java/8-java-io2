package com.example.task01;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Task01Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println(extractSoundName(new File("task01/src/main/resources/3727.mp3")));

    }

    public static String extractSoundName(File file) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(
                "C:/Users/Наталья/Downloads/ffmpeg-9.1-essentials_build/ffmpeg-9.1-essentials_build/bin/ffprobe.exe",
                "-v", "error", "-of", "flat", "-show_format", file.getPath());
        Process process = pb.start();

        String title = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("format.tags.title=")) {
                    title = line.substring("format.tags.title=".length());
                    title = title.substring(1, title.length() - 1);
                }
            }
        }
        process.waitFor();
        return title;
    }
}
