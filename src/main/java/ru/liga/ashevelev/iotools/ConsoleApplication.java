package ru.liga.ashevelev.iotools;

import java.util.Scanner;

/**
 * Класс, запускающий приложение, который содержит в себе запуск сканнера и логику считывания команд с консоли.
 * Класс НА УДАЛЕНИЕ
 */

public class ConsoleApplication {

    CommandHandler commandHandler = new CommandHandler();

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Приветствуем!\nВведите команду:");
            while (true) {
                var command = scanner.nextLine();
                //todo exit лучше вынести в константу
                if("exit".equals(command)) {
                    return;
                }
                commandHandler.handleCommand(command);
            }
        }
    }
}
