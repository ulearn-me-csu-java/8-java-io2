package com.example.task01;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Task01Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println(extractSoundName(new File("task01/src/main/resources/3727.mp3")));

    }

    public static String extractSoundName(File file) throws IOException, InterruptedException {

        if (!file.exists()){
            throw new IOException("File not found: " + file);
        }

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("ffprobe", "-v", "error", "-of", "flat", "-show_format", file.getAbsolutePath());

        Process process = processBuilder.start();
        String line;
        String result = null;
        String target = "format.tags.title=";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"))) {

            while((line = reader.readLine()) != null) {

                if(line.startsWith(target)){

                    String title = line.substring(target.length());
                    title = title.substring(1, title.length() - 1);
                    result = title;
                }
            }
        }

        int exit = process.waitFor();
        if(exit != 0){
            throw new IOException("ffprobe exited with code " + exit);
        }

        return result != null ? result : "Нет названия трека";
    }
}
