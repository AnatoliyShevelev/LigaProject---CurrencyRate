package ru.liga.ashevelev.calculation;

import ru.liga.ashevelev.resources.CurrencyRate;

import java.time.LocalDate;
import java.util.List;

/**
 * Класс, куда будут вносится алгоритмы вычисления.
 * Сейчас здесь один метод - calculateAverageRate,
 * который высчитывает среднее арифметическое из всех найденных записей.
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

    protected CurrencyRate findRateForDate(String currencyName, LocalDate date, List<CurrencyRate> rates) {
        for (CurrencyRate rate : rates) {
            //todo условие лучше вынести в метод и назвать по бизнесу
            if (rate.getName().equals(currencyName) && rate.getDate().equals(date)) {
                return rate;
            }
        }
        return null;
    }
}
