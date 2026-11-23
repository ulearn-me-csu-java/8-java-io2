
package com.example.task01;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Task01Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(extractSoundName(
                new File("task01/src/main/resources/3727.mp3")
        ));
    }

    public static String extractSoundName(File file) throws IOException, InterruptedException {
        String ffprobe = "C:\\ffmpeg-9.1-essentials_build\\ffmpeg-9.1-essentials_build\\bin\\ffprobe.exe";

        ProcessBuilder processBuilder = new ProcessBuilder(
                ffprobe,
                "-v", "error",
                "-of", "flat",
                "-show_format",
                file.getAbsolutePath()
        );

        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        String output = new String(
                process.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );

        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new IOException("Ошибка выполнения ffprobe: " + output);
        }

        for (String line : output.split("\\R")) {
            if (line.startsWith("format.tags.title=")) {
                return line.substring("format.tags.title=".length())
                        .replaceAll("^\"|\"$", "");
            }
        }

        return null;
    }
}
