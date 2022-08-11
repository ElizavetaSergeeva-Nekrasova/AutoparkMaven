package Autopark.FileUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static List<String> readInfo(String inFile) {
        List<String> list = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inFile))) {
            String fileContent = null;
            while ((fileContent = bufferedReader.readLine()) != null) {
                list.add(fileContent);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Cannot read the file", e);
        }

        return list;
    }

    public static void writeLineToFile(String line, String fileName) {
        try {
            Files.write(Paths.get(fileName), line.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeListToFile(List<String> list, String fileName) {
        try {
            Files.write(Paths.get(fileName), list, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}