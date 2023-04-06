package ru.liga.ashevelev.mapper;

import ru.liga.ashevelev.resources.CurrencyRate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Класс, в котором реализована переработка переданного списка строк в список объектов CurrencyRate.
 * Метод toCurrencyRates позволяет читать поля csv-файлов как выделенных кавычками, так и без.
 * Также реализована возможность читать файлы с заголовками и без.
 */

public class CurrencyRateMapper {

    public List<CurrencyRate> toCurrencyRates(List<String> csvLines) {
        return csvLines.stream()
                .map(it -> it.replaceAll("\"", ""))
                .map(this::toCurrencyRate)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private CurrencyRate toCurrencyRate(String line) {
        try {
            String[] fields = line.split(";");
            DateTimeFormatter format = new DateTimeFormatterBuilder()
                    //todo строку можно вынести в константу
                    .appendPattern("[dd/MM/yyyy][dd.MM.yyyy][d/MM/yyyy]")
                    .toFormatter();
            int nominal = Integer.parseInt(fields[0].trim());
            LocalDate date = LocalDate.parse(fields[1].trim(), format);
            double rate = Double.parseDouble(fields[2].trim().replace(',', '.'));
            String name = fields[3].trim();
            return new CurrencyRate(nominal, date, rate, name);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
