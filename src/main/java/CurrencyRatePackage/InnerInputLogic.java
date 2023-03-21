package CurrencyRatePackage;

import java.time.LocalDate;
import java.util.List;
import static CurrencyRatePackage.CurrencyExchange.*;

/**
 * класс, призыванный скрыть всю реализацию с if-ами.
 */

public class InnerInputLogic {
    protected void inputCommand(String command) {
        CurrencyExchange currencyExchange = new CurrencyExchange();

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        if (command.equalsIgnoreCase("rate try tomorrow")) {
            List<CurrencyRate> rates = currencyExchange.readCurrencyRatesFromFile(FILENAME_TRY);
            System.out.println(tomorrow + " - " + currencyExchange.getAverageRateForTomorrow("Турецкая лира", rates));
        } else if (command.equalsIgnoreCase("rate usd tomorrow")) {
            List<CurrencyRate> rates = currencyExchange.readCurrencyRatesFromFile(FILENAME_USD);
            System.out.println(tomorrow + " - " + currencyExchange.getAverageRateForTomorrow("Доллар США", rates));
        } else if (command.equalsIgnoreCase("rate euro tomorrow")) {
            List<CurrencyRate> rates = currencyExchange.readCurrencyRatesFromFile(FILENAME_EUR);
            System.out.println(tomorrow + " - " + currencyExchange.getAverageRateForTomorrow("Евро", rates));
        } else if (command.equalsIgnoreCase("rate try week")) {
            List<CurrencyRate> rates = currencyExchange.readCurrencyRatesFromFile(FILENAME_TRY);
            System.out.println(currencyExchange.getAverageRatesForNextSevenDays("Турецкая лира", rates));
        } else if (command.equalsIgnoreCase("rate usd week")) {
            List<CurrencyRate> rates = currencyExchange.readCurrencyRatesFromFile(FILENAME_USD);
            System.out.println(currencyExchange.getAverageRatesForNextSevenDays("Доллар США", rates));
        } else if (command.equalsIgnoreCase("rate euro week")) {
            List<CurrencyRate> rates = currencyExchange.readCurrencyRatesFromFile(FILENAME_EUR);
            System.out.println(currencyExchange.getAverageRatesForNextSevenDays("Евро", rates));
        } else {
            System.out.println("неизвестная команда");
        }
    }
}
