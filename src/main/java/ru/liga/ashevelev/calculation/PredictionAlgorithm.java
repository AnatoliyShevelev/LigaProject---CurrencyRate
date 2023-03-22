package ru.liga.ashevelev.calculation;

import ru.liga.ashevelev.resources.CurrencyRate;

import java.util.List;

/**
 * Класс, куда будут вносится алгоритмы вычисления.
 * Сейчас здесь один метод - calculateAverageRate,
 * который высчитывает среднее арифметическое из всех найденных записей.
 */

public class PredictionAlgorithm {
    public double calculateAverageRate(List<CurrencyRate> rates) {
        double sum = 0;
        for (CurrencyRate rate : rates) {
            sum += rate.getRate();
        }
        return sum / rates.size();
    }
}
