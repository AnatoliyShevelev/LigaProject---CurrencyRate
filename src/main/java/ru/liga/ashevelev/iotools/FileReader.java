package ru.liga.ashevelev.iotools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, в котором реализован метод считывания с файла csv (ссылка помещается в FILENAME_*) и передача его в список строк.
*/
public class FileReader {

    public static final File FILENAME_TRY = new File(".\\src\\main\\resources\\TRY.csv");
    public static final File FILENAME_USD = new File(".\\src\\main\\resources\\USD.csv");
    public static final File FILENAME_EUR = new File(".\\src\\main\\resources\\EUR.csv");

    public List<String> readCsvLines(File filename) {

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }


}
