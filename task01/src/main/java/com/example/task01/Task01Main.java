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
    }

    public static String extractSoundName(File file) throws IOException, InterruptedException {
        File dir = new File("C:/Users/barge/OneDrive/Desktop/Java-8-NIO/ffmpeg-master-latest-win64-gpl-shared/ffmpeg-master-latest-win64-gpl-shared/bin");
        ProcessBuilder pb = new ProcessBuilder(new File(dir, "ffprobe.exe").getAbsolutePath(),
                "-v", "error",
                "-of", "flat",
                "-show_format",
                file.getAbsolutePath()
        );

        Process process = pb.start();

        StringBuilder str = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                str.append(line).append("\n");
            }
        }

        int exitValue = process.waitFor();
        if (exitValue != 0) {
            System.out.println("ffprobe завершился с кодом: " + exitValue);
        }

        for (String line : str.toString().split("\n")) {
            if (line.startsWith("format.tags.title=")) {
                String value = line.substring("format.tags.title=".length());
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                }
                return value;
            }


        }

        return null;
    }
}
