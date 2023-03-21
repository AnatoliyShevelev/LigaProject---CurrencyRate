package CurrencyRatePackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, в котором реализованы основные методы: считывание с файла csv (ссылка помещается в FILENAME) - readCurrencyRatesFromFile,
 * высчитывание курса на завтра на определённую валюту - getAverageRateForTomorrow
 * и курс на опредлённую валюту на неделю вперёд - getAverageRatesForNextSevenDays.
 *
 * Вспомогательный метод findLatestRate находит в коллекции последнюю нужную нам валюту (Турецкую лиру, Доллар США или Евро).
 * Вспомогательный метод findPreviousRates находит последние count записей в коллекции.
 * Вспомогательный метод calculateAverageRate высчиттывает среднее арифметическое из всех найденных записей.
 *
 */

public class CurrencyExchange {

    public static final String FILENAME = "C:\\Users\\Sharmath\\Desktop\\CurrencyRatesProject\\try.csv";
    private static final int PREVIOUS_RATES_COUNT = 7;

    public List<CurrencyRate> readCurrencyRatesFromFile(String filename) {
        List<CurrencyRate> rates = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
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

    public double getAverageRateForTomorrow(String currencyName, List<CurrencyRate> rates) {
        CurrencyRate todayRate = findLatestRate(currencyName, rates);
        List<CurrencyRate> previousRates = findPreviousRates(currencyName, todayRate.getDate(), PREVIOUS_RATES_COUNT, rates);
        return Math.round(calculateAverageRate(previousRates) * 100.00) / 100.00;
    }

    public List<Double> getAverageRatesForNextSevenDays(String currencyName, List<CurrencyRate> rates) {
        List<Double> averageRates = new ArrayList<>();
        for (int i = 0; i < PREVIOUS_RATES_COUNT; i++) {
            CurrencyRate latestRate = findLatestRate(currencyName, rates);
            List<CurrencyRate> previousRates = findPreviousRates(currencyName, latestRate.getDate(), PREVIOUS_RATES_COUNT + i, rates);
            double averageRate = Math.round(calculateAverageRate(previousRates) * 100.0) / 100.0;
            averageRates.add(averageRate);
        }
        return averageRates;
    }

    private CurrencyRate findLatestRate(String currencyName, List<CurrencyRate> rates) {
        for (int i = 0; i < rates.size(); i++) {
            CurrencyRate rate = rates.get(i);
            if (rate.getName().equals(currencyName)) {
                return rate;
            }
        }
        throw new IllegalArgumentException("No rates found for " + currencyName);
    }

    private List<CurrencyRate> findPreviousRates(String currencyName, LocalDate date, int count, List<CurrencyRate> rates) {
        List<CurrencyRate> previousRates = new ArrayList<>();
        int i = 0;
        while (previousRates.size() < count && i >= 0) {
            CurrencyRate rate = rates.get(i);
            if (rate.getName().equals(currencyName) && !rate.getDate().isAfter(date)) {
                previousRates.add(rate);
            }
            i++;
        }
        if (previousRates.size() < count) {
            throw new IllegalArgumentException(String.format("Not enough rates found for %s before %s", currencyName, date));
        }
        return previousRates;
    }

    private double calculateAverageRate(List<CurrencyRate> rates) {
        double sum = 0;
        for (CurrencyRate rate : rates) {
            sum += rate.getRate();
        }
        return sum / rates.size();
    }
}
