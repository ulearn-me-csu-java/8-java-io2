package com.example.task01;

import java.io.*;

public class Task01Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(extractSoundName(new File("task01/src/main/resources/3727.mp3")));
    }

    public static String extractSoundName(File file) throws IOException, InterruptedException {
        // Путь к ffprobe.exe (двойные слеши обязательны)
        String ffprobePath = "C:\\Users\\user\\OneDrive\\Рабочий стол\\ffmpeg-8.1.2-essentials_build\\ffmpeg-8.1.2-essentials_build\\bin\\ffprobe.exe";

        ProcessBuilder pB = new ProcessBuilder(ffprobePath, "-v", "error", "-of", "flat",
                "-show_format", file.getAbsolutePath());

        pB.redirectErrorStream(true);

        Process process = pB.start();

        String title = null;
        String prefix = "format.tags.title=";

        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = buffer.readLine()) != null) {
                if (line.startsWith(prefix)) {
                    title = line.substring(prefix.length());
                    // Убираем кавычки, если они есть
                    if (title.startsWith("\"") && title.endsWith("\"")) {
                        title = title.substring(1, title.length() - 1);
                    }
                    break;
                }
            }
        }
        process.waitFor();
        return title;
    }
}