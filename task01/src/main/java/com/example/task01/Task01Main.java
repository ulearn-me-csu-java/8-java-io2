package com.example.task01;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Task01Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        //здесь вы можете вручную протестировать ваше решение, вызывая реализуемый метод и смотря результат
        // например вот так:
        System.out.println(extractSoundName(new File("task01/src/main/resources/3727.mp3")));
    }

    public static String extractSoundName(File file) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(
                "ffprobe",
                "-v", "error",
                "-of", "flat",
                "-show_format",
                file.getAbsolutePath()
        );

        // Объединяем стандартный вывод и поток ошибок
        builder.redirectErrorStream(true);

        // Запускаем ffprobe
        Process process = builder.start();

        String title = null;

        // Читаем вывод программы построчно
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        process.getInputStream(),
                        StandardCharsets.UTF_8
                ))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.startsWith("format.tags.title=")) {

                    title = line.substring(
                            "format.tags.title=".length()
                    );

                    // Убираем внешние кавычки
                    if (title.length() >= 2
                            && title.startsWith("\"")
                            && title.endsWith("\"")) {
                        title = title.substring(1, title.length() - 1);
                    }

                    break;
                }
            }
        }

        // Ждём завершения процесса
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new IOException("ffprobe завершился с ошибкой: " + exitCode);
        }

        return title;
    }
}
