package ru.liga.ashevelev.calculation;

import ru.liga.ashevelev.calculation.PredictionAlgorithm;
import ru.liga.ashevelev.resources.CurrencyRate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, в котором реализованы два основных метода:
 * высчитывание курса на завтра на определённую валюту - getAverageRateForTomorrow
 * и курс на определённую валюту на неделю вперёд - getAverageRatesForNextSevenDays.
 *
 * Вспомогательный метод findLatestRate находит в коллекции последнюю нужную нам валюту (Турецкую лиру, Доллар США или Евро).
 * Вспомогательный метод findPreviousRates находит последние count записей в коллекции.
 * Вспомогательный метод calculateAverageRate высчитывает среднее арифметическое из всех найденных записей.
 */

public class CurrencyExchange {

    private static final int PREVIOUS_RATES_COUNT = 7;
    private final PredictionAlgorithm predictionAlgorithm = new PredictionAlgorithm();

    public double calculateAverageRateForTomorrow(String currencyName, List<CurrencyRate> rates) {
        CurrencyRate todayRate = findLatestRate(currencyName, rates);
        List<CurrencyRate> previousRates = findPreviousRates(currencyName, todayRate.getDate(), PREVIOUS_RATES_COUNT, rates);
        return predictionAlgorithm.calculateAverageRate(previousRates);
    }

    public Map<String, Double> calculateAverageRatesForNextSevenDays(String currencyName, List<CurrencyRate> rates) {
        Map<String, Double> averageRates = new LinkedHashMap<>();
        LocalDate currentDate = LocalDate.now();
        for (int i = 0; i < PREVIOUS_RATES_COUNT; i++) {
            CurrencyRate latestRate = findLatestRate(currencyName, rates);
            List<CurrencyRate> previousRates = findPreviousRates(currencyName, latestRate.getDate(), PREVIOUS_RATES_COUNT + i, rates);
            double averageRate = predictionAlgorithm.calculateAverageRate(previousRates);
            String date = currentDate.plusDays(i + 1).format(DateTimeFormatter.ofPattern("E dd.MM.yyyy"));
            averageRates.put(date, averageRate);
        }
        return averageRates;
    }

    private CurrencyRate findLatestRate(String currencyName, List<CurrencyRate> rates) {
        for (CurrencyRate rate : rates) {
            if (rate.getName().equals(currencyName)) {
                return rate;
            }
        }
        throw new IllegalArgumentException("No rates found for " + currencyName);
    }

    private List<CurrencyRate> findPreviousRates(String currencyName, LocalDate date, int count, List<CurrencyRate> rates) {
        List<CurrencyRate> previousRates = new ArrayList<>();
        for (int i = 0; previousRates.size() < count && i < rates.size(); i++) {
            CurrencyRate rate = rates.get(i);
            if (rate.getName().equals(currencyName) && !rate.getDate().isAfter(date)) {
                previousRates.add(rate);
            }
        }
        if (previousRates.size() < count) {
            throw new IllegalArgumentException(String.format("Not enough rates found for %s before %s", currencyName, date));
        }
        return previousRates;
    }
}
