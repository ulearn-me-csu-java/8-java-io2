package com.example.task01;

import java.io.File;
import java.io.IOException;

import java.io.BufferedReader;
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
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("ffprobe", "-v", "error", "-of", "flat", "-show_format", file.getAbsolutePath());

        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("format.tags.title=")) {
                    // Строка вида: format.tags.title="Canon trias harmonica a 8"
                    // Извлекаем то, что внутри кавычек
                    int firstQuote = line.indexOf('"');
                    int lastQuote = line.lastIndexOf('"');
                    if (firstQuote != -1 && lastQuote > firstQuote) {
                        return line.substring(firstQuote + 1, lastQuote);
                    }
                }
            }
        }

        process.waitFor();
        return null;
    }
}
