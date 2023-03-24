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

    public static final File FILENAME_TRY = new File(".\\src\\main\\resources\\try1.csv");
    public static final File FILENAME_USD = new File(".\\src\\main\\resources\\usd1.csv");
    public static final File FILENAME_EUR = new File(".\\src\\main\\resources\\eur1.csv");

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
