package ru.liga.ashevelev.iotools;

import java.util.Scanner;

/**
 * Класс, запускающий приложение, который содержит в себе запуск сканнера и логику считывания команд с консоли.
 */

public class ConsoleApplication {

    CommandHandler commandHandler = new CommandHandler();

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Приветствуем!\nВведите команду:");
            while (true) {
                var command = scanner.nextLine();
                if("exit".equals(command)) {
                    return;
                }
                commandHandler.handleCommand(command);
            }
        }
    }
}
