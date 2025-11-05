package team.shellsort.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class FileLines {
    private FileLines() {}
    public static List<String> readAll(Path path) throws IOException {
        return Files.readAllLines(path);
    }
}
