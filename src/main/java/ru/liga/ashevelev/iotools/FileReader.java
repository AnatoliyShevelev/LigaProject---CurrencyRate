package ru.liga.ashevelev.iotools;

import ru.liga.ashevelev.resources.CurrencyRate;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, в котором реализован метод считывания с файла csv (ссылка помещается в FILENAME_*).
*/
public class FileReader {

    public static final File FILENAME_TRY = new File(".\\src\\main\\resources\\try.csv");
    public static final File FILENAME_USD = new File(".\\src\\main\\resources\\usd.csv");
    public static final File FILENAME_EUR = new File(".\\src\\main\\resources\\eur.csv");

    public List<CurrencyRate> readCurrencyRatesFromFile(File filename) {
        List<CurrencyRate> rates = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(";");
                DateTimeFormatter format = new DateTimeFormatterBuilder()
                        .appendPattern("[dd/MM/yyyy][dd.MM.yyyy][d/MM/yyyy]")
                        .toFormatter();
                int nominal = Integer.parseInt(fields[0].trim());
                LocalDate date = LocalDate.parse(fields[1].trim(), format);
                double rate = Double.parseDouble(fields[2].trim().replace(',', '.'));
                String name = fields[3].trim();
                rates.add(new CurrencyRate(nominal, date, rate, name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rates;
    }
}
