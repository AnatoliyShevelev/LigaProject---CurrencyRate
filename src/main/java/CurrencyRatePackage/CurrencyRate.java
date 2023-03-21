package CurrencyRatePackage;

import java.time.LocalDate;

/**
 * Класс в который кладутся записи из пяти столбцов (nominal, дата, курс, название валюты).
 */

public class CurrencyRate {
    private final int nominal;
    private final LocalDate date;
    private final double rate;
    private final String name;

    public CurrencyRate(int nominal, LocalDate date, double rate, String name) {
        this.nominal = nominal;
        this.date = date;
        this.rate = rate;
        this.name = name;
    }
    public int getNominal() {
        return nominal;
    }
    public LocalDate getDate() {
        return date;
    }

    public double getRate() {
        return rate;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return " " + getNominal() + " " + getDate() + " " + getRate() + " " + getName() + "\n";
    }

}
