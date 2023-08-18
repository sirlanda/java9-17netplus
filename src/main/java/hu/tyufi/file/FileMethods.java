package hu.tyufi.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileMethods {

    public static void main(String[] args) throws IOException {
        String sampleText = "Sample text";
        Path filePath = Files.writeString(Files.createTempFile(Path.of("/tmp"), "demo", ".txt"), sampleText);
        String fileContent = Files.readString(filePath);
        assert fileContent.equals(sampleText);
    }
}
