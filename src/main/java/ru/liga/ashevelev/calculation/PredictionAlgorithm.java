package ru.liga.ashevelev.calculation;

import ru.liga.ashevelev.resources.CurrencyRate;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Класс, который содержит алгоритмы вычисления.
 */

public class PredictionAlgorithm {
    protected double calculateAverageRate(List<CurrencyRate> rates) {
        double sum = 0;
        for (CurrencyRate rate : rates) {
            sum += rate.getRate();
        }
        return sum / rates.size();
    }

    protected CurrencyRate findLastYearRate(String currencyName, List<CurrencyRate> rates) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate dateLastYear = tomorrow.minusYears(1);
        CurrencyRate rateLastYear = findRateForDate(currencyName, dateLastYear, rates);
        if (rateLastYear == null) {
            rateLastYear = findRateForDate(currencyName, dateLastYear.minusDays(1), rates);
        }
        return rateLastYear;
    }

    protected CurrencyRate findRandomRateForDate(String currencyName, List<CurrencyRate> rates, LocalDate date) {
        int randomYear = ThreadLocalRandom.current().nextInt(2005, date.getYear());
        LocalDate randomDate = date.withYear(randomYear);
        CurrencyRate randomRate = findRateForDate(currencyName, randomDate, rates);
        if (randomRate == null) {
            randomRate = findRateForDate(currencyName, randomDate.minusDays(1), rates);
        }
        return randomRate;
    }

    protected CurrencyRate findRateForDate(String currencyName, LocalDate date, List<CurrencyRate> rates) {
        CurrencyRate rate = null;
        for (CurrencyRate r : rates) {
            if (hasRateFound(r, currencyName, date)) {
                rate = r;
                break;
            }
        }
        if (rate == null) {
            LocalDate prevDate = date.minusDays(1);
            rate = findRateForDate(currencyName, prevDate, rates);
        }
        return rate;
    }

    private boolean hasRateFound(CurrencyRate rate, String currencyName, LocalDate date) {
        return rate.getName().equals(currencyName) && rate.getDate().equals(date);
    }
}
