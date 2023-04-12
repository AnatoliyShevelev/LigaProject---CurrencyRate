package ru.liga.ashevelev.iotools;

import ru.liga.ashevelev.calculation.CurrencyExchange;
import ru.liga.ashevelev.mapper.CurrencyRateMapper;
import ru.liga.ashevelev.resources.CurrencyRate;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.liga.ashevelev.iotools.FileReader.*;

/**
 * В этом коде мы создаем Map, где ключом является команда, а значением - функция,
 * которую нужно выполнить при вызове этой команды. Затем мы создаем методы,
 * которые реализуют логику для каждой команды, и используем их в качестве значений в Map.
 * В методе handleCommand мы просто получаем функцию из Map по ключу,
 * соответствующему введенной команде, и вызываем ее.
 * Если ключа в Map нет, то мы вызываем функцию, соответствующую команде "unknown",
 * которая выводит сообщение о том, что команда неизвестна.
 */

public class CommandHandler {

    public String result;
    private static final int FOR_WEEK = 7;
    private static final int FOR_MONTH = 30;

    private final CurrencyExchange currencyExchange = new CurrencyExchange();
    private final FileReader fileReader = new FileReader();
    private final CurrencyRateMapper mapper = new CurrencyRateMapper();

    private final Map<String, Runnable> commands = new HashMap<>();

    private final LocalDate tomorrow = LocalDate.now().plusDays(1);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd.MM.yyyy");

    //todo не понял что это, ты здесь вписал все возможные команды, которые могут прийти в консоль? а если будет две валюты, или перепутано местами что то?
    //тебе надо было написать парсер, который из любой строки вытаскивает валюту дату и т.д
    // а потом уже с полученными данными идет дальше.
    // хардкодить все команды которые ты ожидаешь нельзя
    public CommandHandler() {
        //на завтра по среднему алгоритму
        commands.put("rate try -date tomorrow -alg avg", () -> printAverageRateForTomorrow(String.valueOf(FILENAME_TRY), "Турецкая лира"));
        commands.put("rate usd -date tomorrow -alg avg", () -> printAverageRateForTomorrow(String.valueOf(FILENAME_USD), "Доллар США"));
        commands.put("rate euro -date tomorrow -alg avg", () -> printAverageRateForTomorrow(String.valueOf(FILENAME_EUR), "Евро"));
        commands.put("rate amd -date tomorrow -alg avg", () -> printAverageRateForTomorrow(String.valueOf(FILENAME_AMD), "Армянский драм"));
        commands.put("rate bgn -date tomorrow -alg avg", () -> printAverageRateForTomorrow(String.valueOf(FILENAME_BGN), "Болгарский лев"));
        //на завтра по прошлогоднему алгоритму
        commands.put("rate try -date tomorrow -alg last year", () -> printLastYearRateForTomorrow(String.valueOf(FILENAME_TRY), "Турецкая лира"));
        commands.put("rate usd -date tomorrow -alg last year", () -> printLastYearRateForTomorrow(String.valueOf(FILENAME_USD), "Доллар США"));
        commands.put("rate euro -date tomorrow -alg last year", () -> printLastYearRateForTomorrow(String.valueOf(FILENAME_EUR), "Евро"));
        commands.put("rate amd -date tomorrow -alg last year", () -> printLastYearRateForTomorrow(String.valueOf(FILENAME_AMD), "Армянский драм"));
        commands.put("rate bgn -date tomorrow -alg last year", () -> printLastYearRateForTomorrow(String.valueOf(FILENAME_BGN), "Болгарский лев"));
        //на завтра по мистическому алгоритму
        commands.put("rate try -date tomorrow -alg mist", () -> printRandomRateForTomorrow(String.valueOf(FILENAME_TRY), "Турецкая лира"));
        commands.put("rate usd -date tomorrow -alg mist", () -> printRandomRateForTomorrow(String.valueOf(FILENAME_USD), "Доллар США"));
        commands.put("rate euro -date tomorrow -alg mist", () -> printRandomRateForTomorrow(String.valueOf(FILENAME_EUR), "Евро"));
        commands.put("rate amd -date tomorrow -alg mist", () -> printRandomRateForTomorrow(String.valueOf(FILENAME_AMD), "Армянский драм"));
        commands.put("rate bgn -date tomorrow -alg mist", () -> printRandomRateForTomorrow(String.valueOf(FILENAME_BGN), "Болгарский лев"));
        //на период недели по среднему алгоритму
        commands.put("rate try -period week -alg avg -output list", () -> printAverageRatesForPeriod(String.valueOf(FILENAME_TRY), "Турецкая лира", FOR_WEEK));
        commands.put("rate usd -period week -alg avg -output list", () -> printAverageRatesForPeriod(String.valueOf(FILENAME_USD), "Доллар США", FOR_WEEK));
        commands.put("rate euro -period week -alg avg -output list", () -> printAverageRatesForPeriod(String.valueOf(FILENAME_EUR), "Евро", FOR_WEEK));
        commands.put("rate amd -period week -alg avg -output list", () -> printAverageRatesForPeriod(String.valueOf(FILENAME_AMD), "Армянский драм", FOR_WEEK));
        commands.put("rate bgn -period week -alg avg -output list", () -> printAverageRatesForPeriod(String.valueOf(FILENAME_BGN), "Болгарский лев", FOR_WEEK));
        //на период месяца по среднему алгоритму
        commands.put("rate try -period month -alg avg -output list", () -> printAverageRatesForPeriod(String.valueOf(FILENAME_TRY), "Турецкая лира", FOR_MONTH));
        commands.put("rate usd -period month -alg avg -output list", () -> printAverageRatesForPeriod(String.valueOf(FILENAME_USD), "Доллар США", FOR_MONTH));
        commands.put("rate euro -period month -alg avg -output list", () -> printAverageRatesForPeriod(String.valueOf(FILENAME_EUR), "Евро", FOR_MONTH));
        commands.put("rate amd -period month -alg avg -output list", () -> printAverageRatesForPeriod(String.valueOf(FILENAME_AMD), "Армянский драм", FOR_MONTH));
        commands.put("rate bgn -period month -alg avg -output list", () -> printAverageRatesForPeriod(String.valueOf(FILENAME_BGN), "Болгарский лев", FOR_MONTH));
        //на период недели по прошлогоднему алгоритму
        commands.put("rate try -period week -alg last year -output list", () -> printLastYearRatesForPeriod(String.valueOf(FILENAME_TRY), "Турецкая лира", FOR_WEEK));
        commands.put("rate usd -period week -alg last year -output list", () -> printLastYearRatesForPeriod(String.valueOf(FILENAME_USD), "Доллар США", FOR_WEEK));
        commands.put("rate euro -period week -alg last year -output list", () -> printLastYearRatesForPeriod(String.valueOf(FILENAME_EUR), "Евро", FOR_WEEK));
        commands.put("rate amd -period week -alg last year -output list", () -> printLastYearRatesForPeriod(String.valueOf(FILENAME_AMD), "Армянский драм", FOR_WEEK));
        commands.put("rate bgn -period week -alg last year -output list", () -> printLastYearRatesForPeriod(String.valueOf(FILENAME_BGN), "Болгарский лев", FOR_WEEK));
        //на период месяца по прошлогоднему алгоритму
        commands.put("rate try -period month -alg last year -output list", () -> printLastYearRatesForPeriod(String.valueOf(FILENAME_TRY), "Турецкая лира", FOR_MONTH));
        commands.put("rate usd -period month -alg last year -output list", () -> printLastYearRatesForPeriod(String.valueOf(FILENAME_USD), "Доллар США", FOR_MONTH));
        commands.put("rate euro -period month -alg last year -output list", () -> printLastYearRatesForPeriod(String.valueOf(FILENAME_EUR), "Евро", FOR_MONTH));
        commands.put("rate amd -period month -alg last year -output list", () -> printLastYearRatesForPeriod(String.valueOf(FILENAME_AMD), "Армянский драм", FOR_MONTH));
        commands.put("rate bgn -period month -alg last year -output list", () -> printLastYearRatesForPeriod(String.valueOf(FILENAME_BGN), "Болгарский лев", FOR_MONTH));
        //на период недели по мистическому алгоритму
        commands.put("rate try -period week -alg mist -output list", () -> printRandomRatesForPeriod(String.valueOf(FILENAME_TRY), "Турецкая лира", FOR_WEEK));
        commands.put("rate usd -period week -alg mist -output list", () -> printRandomRatesForPeriod(String.valueOf(FILENAME_USD), "Доллар США", FOR_WEEK));
        commands.put("rate euro -period week -alg mist -output list", () -> printRandomRatesForPeriod(String.valueOf(FILENAME_EUR), "Евро", FOR_WEEK));
        commands.put("rate amd -period week -alg mist -output list", () -> printRandomRatesForPeriod(String.valueOf(FILENAME_AMD), "Армянский драм", FOR_WEEK));
        commands.put("rate bgn -period week -alg mist -output list", () -> printRandomRatesForPeriod(String.valueOf(FILENAME_BGN), "Болгарский лев", FOR_WEEK));
        //на период месяца по мистическому алгоритму
        commands.put("rate try -period month -alg mist -output list", () -> printRandomRatesForPeriod(String.valueOf(FILENAME_TRY), "Турецкая лира", FOR_MONTH));
        commands.put("rate usd -period month -alg mist -output list", () -> printRandomRatesForPeriod(String.valueOf(FILENAME_USD), "Доллар США", FOR_MONTH));
        commands.put("rate euro -period month -alg mist -output list", () -> printRandomRatesForPeriod(String.valueOf(FILENAME_EUR), "Евро", FOR_MONTH));
        commands.put("rate amd -period month -alg mist -output list", () -> printRandomRatesForPeriod(String.valueOf(FILENAME_AMD), "Армянский драм", FOR_MONTH));
        commands.put("rate bgn -period month -alg mist -output list", () -> printRandomRatesForPeriod(String.valueOf(FILENAME_BGN), "Болгарский лев", FOR_MONTH));

        commands.put("unknown", () -> System.out.println("неизвестная команда"));
    }

    //todo надо переделать под порсер команды, нельзя прописывать все команды
    // если прога будет расширяться, например будут добавляться валюты, придется вручную все прописывать, что займет очень много времени + можно допустить ошибку
    public String handleCommand(String command) {
        commands.getOrDefault(command.toLowerCase(), commands.get("unknown")).run();
        String res = result;
        result = "";
        return res;
    }

    private void printAverageRateForTomorrow(String filename, String currency) {
        List<String> csvLines = fileReader.readCsvLines(new File(filename));
        List<CurrencyRate> rates = mapper.toCurrencyRates(csvLines);
        // System.out.println(tomorrow.format(formatter) + " - " + String.format("%.2f",currencyExchange.calculateAverageRateForTomorrow(currency, rates)));
        result = tomorrow.format(formatter) + " - " + String.format("%.2f", currencyExchange.calculateAverageRateForTomorrow(currency, rates));
    }

    private void printLastYearRateForTomorrow(String filename, String currency) {
        List<String> csvLines = fileReader.readCsvLines(new File(filename));
        List<CurrencyRate> rates = mapper.toCurrencyRates(csvLines);
        result = tomorrow.format(formatter) + " - " + String.format("%.2f", currencyExchange.calculateLastYearRateForTomorrow(currency, rates));
    }

    private void printRandomRateForTomorrow(String filename, String currency) {
        List<String> csvLines = fileReader.readCsvLines(new File(filename));
        List<CurrencyRate> rates = mapper.toCurrencyRates(csvLines);
        result = tomorrow.format(formatter) + " - " + String.format("%.2f", currencyExchange.calculateRandomRateForTomorrow(currency, rates));
    }

    private void printAverageRateForFutureDate(String filename, String currency, LocalDate date) {
        List<String> csvLines = fileReader.readCsvLines(new File(filename));
        List<CurrencyRate> rates = mapper.toCurrencyRates(csvLines);
        result = date.format(formatter) + " - " + String.format("%.2f", currencyExchange.calculateAverageRateForFutureDate(currency, rates, date));
    }

    private void printLastYearRateForFutureDate(String filename, String currency, LocalDate date) {
        List<String> csvLines = fileReader.readCsvLines(new File(filename));
        List<CurrencyRate> rates = mapper.toCurrencyRates(csvLines);
        result = date.format(formatter) + " - " + String.format("%.2f", currencyExchange.calculateLastYearRateForFutureDate(currency, rates, date));
    }

    private void printAverageRatesForPeriod(String filename, String currency, int period) {
        List<String> csvLines = fileReader.readCsvLines(new File(filename));
        List<CurrencyRate> rates = mapper.toCurrencyRates(csvLines);
        Map<String, Double> averageRates = currencyExchange.calculateAverageRatesForPeriod(currency, rates, period);
//        for (Map.Entry<String, Double> entry : averageRates.entrySet()) {
//            System.out.println(entry.getKey() + " - " + String.format("%.2f",entry.getValue()));
//            result = entry.getKey() + " - " + String.format("%.2f",entry.getValue());
//
//        }
        result = formatRates(averageRates);
    }

    private void printLastYearRatesForPeriod(String filename, String currency, int period) {
        List<String> csvLines = fileReader.readCsvLines(new File(filename));
        List<CurrencyRate> rates = mapper.toCurrencyRates(csvLines);
        Map<String, Double> averageRates = currencyExchange.calculateLastYearRatesForPeriod(currency, rates, period);
        result = formatRates(averageRates);
    }

    private void printRandomRatesForPeriod(String filename, String currency, int period) {
        List<String> csvLines = fileReader.readCsvLines(new File(filename));
        List<CurrencyRate> rates = mapper.toCurrencyRates(csvLines);
        Map<String, Double> averageRates = currencyExchange.calculateRandomRatesForPeriod(currency, rates, period);
        result = formatRates(averageRates);
    }

    private String formatRates(Map<String, Double> averageRates) {
        return averageRates.entrySet()
                .stream()
                .map(this::formatRate)
                .collect(Collectors.joining("\n"));
    }

    private String formatRate(Map.Entry<String, Double> entry) {
        return entry.getKey() + " - " + String.format("%.2f", entry.getValue());
    }
}