package ru.liga.ashevelev.iotools;

import ru.liga.ashevelev.calculation.CurrencyExchange;
import ru.liga.ashevelev.resources.CurrencyRate;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.liga.ashevelev.iotools.FileReader.*;

/**
 * В этом коде мы создаем Map, где ключом является команда, а значением - функция,
 * которую нужно выполнить при вызове этой команды. Затем мы создаем методы,
 * которые реализуют логику для каждой команды, и используем их в качестве значений в Map.
 * В методе inputCommand мы просто получаем функцию из Map по ключу,
 * соответствующему введенной команде, и вызываем ее.
 * Если ключа в Map нет, то мы вызываем функцию, соответствующую команде "unknown",
 * которая выводит сообщение о том, что команда неизвестна.
 */

public class CommandHandler {

    private final CurrencyExchange currencyExchange = new CurrencyExchange();
    private final FileReader fileReader = new FileReader();

    private final Map<String, Runnable> commands = new HashMap<>();

    private final LocalDate tomorrow = LocalDate.now().plusDays(1);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd.MM.yyyy");

    public CommandHandler() {
        commands.put("rate try tomorrow", () -> printAverageRateForTomorrow(String.valueOf(FILENAME_TRY), "Турецкая лира"));
        commands.put("rate usd tomorrow", () -> printAverageRateForTomorrow(String.valueOf(FILENAME_USD), "Доллар США"));
        commands.put("rate euro tomorrow", () -> printAverageRateForTomorrow(String.valueOf(FILENAME_EUR), "Евро"));
        commands.put("rate try week", () -> printAverageRatesForNextSevenDays(String.valueOf(FILENAME_TRY), "Турецкая лира"));
        commands.put("rate usd week", () -> printAverageRatesForNextSevenDays(String.valueOf(FILENAME_USD), "Доллар США"));
        commands.put("rate euro week", () -> printAverageRatesForNextSevenDays(String.valueOf(FILENAME_EUR), "Евро"));
        commands.put("unknown", () -> System.out.println("неизвестная команда"));
    }

    protected void handleCommand(String command) {
        commands.getOrDefault(command.toLowerCase(), commands.get("unknown")).run();
    }

    private void printAverageRateForTomorrow(String filename, String currency) {
        List<CurrencyRate> rates = fileReader.readCurrencyRatesFromFile(new File(filename));
        System.out.println(tomorrow.format(formatter) + " - " + currencyExchange.calculateAverageRateForTomorrow(currency, rates));
    }

    private void printAverageRatesForNextSevenDays(String filename, String currency) {
        List<CurrencyRate> rates = fileReader.readCurrencyRatesFromFile(new File(filename));
        Map<String, Double> averageRates = currencyExchange.calculateAverageRatesForNextSevenDays(currency, rates);
        for (Map.Entry<String, Double> entry : averageRates.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
}
