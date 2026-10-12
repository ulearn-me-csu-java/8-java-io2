package com.example.task01;

import java.io.File;
import java.io.IOException;

public class Task01Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        //здесь вы можете вручную протестировать ваше решение, вызывая реализуемый метод и смотря результат
        // например вот так:

        /*
        System.out.println(extractSoundName(new File("task01/src/main/resources/3727.mp3")));
        */
    }

    public static String extractSoundName(File file) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(
                "\"D:\\Apps\\ffmpeg-master-latest-win64-gpl-shared\\bin\\ffprobe.exe\"", "-v", "error", "-of", "flat", "-show_format", file.getAbsolutePath());
        Process process = pb.start();

        String output = new String(process.getInputStream().readAllBytes());

        for (String line : output.split("\n")) {
            if (line.startsWith("format.tags.title=")) {
                String value = line.substring("format.tags.title=".length()).trim();
                return value.substring(1,value.length() - 1);
            }
        }
        return null;
    }
}