package com.example.task01;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Task01Main
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        System.out.println(extractSoundName(new File("task01/src/main/resources/3727.mp3")));

    }

    public static String extractSoundName(File file) throws IOException, InterruptedException
    {
        ProcessBuilder processBuilder = new ProcessBuilder("ffprobe", "-v", "error", "-of", "flat", "-show_format", file.getPath());
        processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);

        Process process = processBuilder.start();
        String title = null;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8)))
        {
            String line;

            while ((line = reader.readLine()) != null)
            {
                int separatorIndex = line.indexOf('=');

                if (separatorIndex == -1)
                {
                    continue;
                }

                String key = line.substring(0, separatorIndex);

                if (key.equalsIgnoreCase("format.tags.title"))
                {
                    String value = line.substring(separatorIndex + 1);

                    if (value.length() >= 2 && value.startsWith("\"") && value.endsWith("\""))
                    {
                        value = value.substring(1, value.length() - 1);
                    }

                    title = value;
                }
            }
        }

        process.waitFor();

        return title;
    }
}
