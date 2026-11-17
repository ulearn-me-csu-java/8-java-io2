import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public static String extractSoundName(File file) throws IOException, InterruptedException {
    ProcessBuilder pb = new ProcessBuilder(
            "ffprobe", "-v", "error", "-of", "flat", "-show_format", file.getPath());
    pb.redirectErrorStream(true);
    Process process = pb.start();

    String title = null;
    try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.toLowerCase().startsWith("format.tags.title=")) {
                title = line.substring(line.indexOf('=') + 1).trim();
                if (title.length() >= 2 && title.startsWith("\"") && title.endsWith("\"")) {
                    title = title.substring(1, title.length() - 1);
                }
            }
        }
    }
    process.waitFor();
    return title;
}