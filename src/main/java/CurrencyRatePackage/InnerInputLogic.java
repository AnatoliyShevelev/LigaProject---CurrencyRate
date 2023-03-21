package CurrencyRatePackage;

import java.time.LocalDate;
import java.util.List;
import static CurrencyRatePackage.CurrencyExchange.FILENAME;

/**
 * класс, призыванный скрыть всю реализацию с if-ами.
 */

public class InnerInputLogic {
    protected void inputCommand(String command) {
        CurrencyExchange currencyExchange = new CurrencyExchange();
        List<CurrencyRate> rates = currencyExchange.readCurrencyRatesFromFile(FILENAME);
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        if (command.equalsIgnoreCase("rate try tomorrow")) {
            System.out.println(tomorrow + " - " + currencyExchange.getAverageRateForTomorrow("Турецкая лира", rates));
        } else if (command.equalsIgnoreCase("rate usd tomorrow")) {
            System.out.println(tomorrow + " - " + currencyExchange.getAverageRateForTomorrow("Доллар США", rates));
        } else if (command.equalsIgnoreCase("rate euro tomorrow")) {
            System.out.println(tomorrow + " - " + currencyExchange.getAverageRateForTomorrow("Евро", rates));
        } else if (command.equalsIgnoreCase("rate try week")) {
            System.out.println(currencyExchange.getAverageRatesForNextSevenDays("Турецкая лира", rates));
        } else if (command.equalsIgnoreCase("rate usd week")) {
            System.out.println(currencyExchange.getAverageRatesForNextSevenDays("Доллар США", rates));
        } else if (command.equalsIgnoreCase("rate euro week")) {
            System.out.println(currencyExchange.getAverageRatesForNextSevenDays("Евро", rates));
        } else {
            System.out.println("неизвестная команда");
        }
    }
}
