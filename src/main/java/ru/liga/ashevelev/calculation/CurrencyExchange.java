package ru.liga.ashevelev.calculation;

import ru.liga.ashevelev.resources.CurrencyRate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Класс, в котором реализованы основные методы.
 */

public class CurrencyExchange {

    private static final int PREVIOUS_RATES_COUNT = 7;
    private final PredictionAlgorithm predictionAlgorithm = new PredictionAlgorithm();

    public double calculateAverageRateForTomorrow(String currencyName, List<CurrencyRate> rates) {
        CurrencyRate todayRate = findLatestRate(currencyName, rates);
        List<CurrencyRate> previousRates = findPreviousRates(currencyName, todayRate.getDate(), PREVIOUS_RATES_COUNT, rates);
        return predictionAlgorithm.calculateAverageRate(previousRates);
    }

    public double calculateLastYearRateForTomorrow(String currencyName, List<CurrencyRate> rates) {
        CurrencyRate rateLastYear = predictionAlgorithm.findLastYearRate(currencyName, rates);
        return rateLastYear.getRate();
    }

    public double calculateRandomRateForTomorrow(String currencyName, List<CurrencyRate> rates) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        CurrencyRate randomRate = predictionAlgorithm.findRandomRateForDate(currencyName, rates, tomorrow);
        return randomRate.getRate();
    }

    public double calculateAverageRateForFutureDate(String currencyName, List<CurrencyRate> rates, LocalDate futureDate) {
        CurrencyRate latestRate = findLatestRate(currencyName, rates);
        List<CurrencyRate> previousRates = findPreviousRates(currencyName, latestRate.getDate(), PREVIOUS_RATES_COUNT, rates);
        List<CurrencyRate> futureRates = findFutureRates(currencyName, futureDate, PREVIOUS_RATES_COUNT, rates);
        previousRates.addAll(futureRates);
        return predictionAlgorithm.calculateAverageRate(previousRates);
    }

    public double calculateLastYearRateForFutureDate(String currencyName, List<CurrencyRate> rates, LocalDate date) {
        CurrencyRate lastYearRate = predictionAlgorithm.findLastYearRate(currencyName, rates);
        LocalDate lastYearDate = date.minusYears(1);
        if (lastYearRate.getDate().equals(lastYearDate)) {
            return lastYearRate.getRate();
        } else {
            CurrencyRate rate = predictionAlgorithm.findRateForDate(currencyName, lastYearDate, rates);
            if (rate != null) {
                return rate.getRate();
            } else {
                return 0.0;
            }
        }
    }

    public Map<String, Double> calculateAverageRatesForPeriod(String currencyName, List<CurrencyRate> rates, int period) {
        Map<String, Double> averageRates = new LinkedHashMap<>();
        LocalDate currentDate = LocalDate.now();
        for (int i = 0; i < period; i++) {
            CurrencyRate latestRate = findLatestRate(currencyName, rates);
            List<CurrencyRate> previousRates = findPreviousRates(currencyName, latestRate.getDate(), PREVIOUS_RATES_COUNT + i, rates);
            double averageRate = predictionAlgorithm.calculateAverageRate(previousRates);
            //todo "E dd.MM.yyyy" используется три раза в этом классе, можно вынести в константу
            String date = currentDate.plusDays(i + 1).format(DateTimeFormatter.ofPattern("E dd.MM.yyyy"));
            averageRates.put(date, averageRate);
        }
        return averageRates;
    }

    public Map<String, Double> calculateLastYearRatesForPeriod(String currencyName, List<CurrencyRate> rates, int period) {
        Map<String, Double> lastYearRates = new LinkedHashMap<>();
        LocalDate currentDate = LocalDate.now();
        for (int i = 0; i < period; i++) {
            CurrencyRate rateLastYear = predictionAlgorithm.findLastYearRate(currencyName, rates);
            double lastYearRate = rateLastYear.getRate();
            String date = currentDate.plusDays(i + 1).format(DateTimeFormatter.ofPattern("E dd.MM.yyyy"));
            lastYearRates.put(date, lastYearRate);
        }
        return lastYearRates;
    }

    public Map<String, Double> calculateRandomRatesForPeriod(String currencyName, List<CurrencyRate> rates, int period) {
        Map<String, Double> randomRates = new LinkedHashMap<>();
        LocalDate currentDate = LocalDate.now();
        for (int i = 0; i < period; i++) {
            LocalDate date = currentDate.plusDays(i + 1);
            CurrencyRate randomRate = predictionAlgorithm.findRandomRateForDate(currencyName, rates, date);
            double rate = randomRate.getRate();
            String dateString = date.format(DateTimeFormatter.ofPattern("E dd.MM.yyyy"));
            randomRates.put(dateString, rate);
        }
        return randomRates;
    }

    private List<CurrencyRate> findFutureRates(String currencyName, LocalDate date, int count, List<CurrencyRate> rates) {
        List<CurrencyRate> futureRates = new ArrayList<>();
        for (int i = 0; futureRates.size() < count && i < rates.size(); i++) {
            CurrencyRate rate = rates.get(i);
            //todo условие лучше вынести в метод и назвать по бизнесу
            if (rate.getName().equals(currencyName) && rate.getDate().isAfter(date)) {
                futureRates.add(rate);
            }
        }
        if (futureRates.size() < count) {
            throw new IllegalArgumentException(String.format("Not enough rates found for %s after %s", currencyName, date));
        }
        return futureRates;
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
            //todo условие лучше вынести в метод и назвать по бизнесу
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
