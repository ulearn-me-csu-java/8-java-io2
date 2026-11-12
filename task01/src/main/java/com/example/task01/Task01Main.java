package com.example.task01;

import java.io.*;

public class Task01Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        //здесь вы можете вручную протестировать ваше решение, вызывая реализуемый метод и смотря результат
        // например вот так:


        System.out.println(extractSoundName(new File("task01/src/main/resources/3727.mp3")));

    }

    public static String extractSoundName(File file) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(
                "ffprobe",
                "-v",
                "error",
                "-of",
                "flat",
                "-show_format",
                file.getAbsolutePath()
        );
        pb.redirectOutput(ProcessBuilder.Redirect.PIPE);

        String res = "";
        Process p = pb.start();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String s;
            String title = "format.tags.title=";
            while ((s = in.readLine()) != null) {
                if (s.startsWith(title)) {
                    res = s.substring(title.length())
                            .replace("\"", "");
                }
            }
        }

        return res;
    }
}
