package ru.liga.ashevelev.iotools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс, в котором реализован метод считывания с файла csv (ссылка помещается в FILENAME_*) и передача его в список строк.
*/
public class FileReader {

    public static final File FILENAME_TRY = new File(".\\src\\main\\resources\\TRY.csv");
    public static final File FILENAME_USD = new File(".\\src\\main\\resources\\USD.csv");
    public static final File FILENAME_EUR = new File(".\\src\\main\\resources\\EUR.csv");
    public static final File FILENAME_AMD = new File(".\\src\\main\\resources\\AMD.csv");
    public static final File FILENAME_BGN = new File(".\\src\\main\\resources\\BGN.csv");
    public static final File COMMANDS= new File(".\\src\\main\\resources\\Commands.csv");


    public List<String> readCsvLines(File filename) {

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "catch IOException", e);
            throw new RuntimeException(e.getMessage(), e);
        }

        return lines;
    }


}
