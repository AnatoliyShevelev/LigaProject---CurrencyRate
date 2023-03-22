package ru.liga.ashevelev.iotools;

import java.util.Scanner;

/**
 * Класс, запускающий приложение, который содержит в себе запуск сканнера и логику считывания команд с консоли.
 */

public class RunningProgram {

    private final Scanner scanner;

    public RunningProgram() {
        System.out.println("Приветствуем!\nВведите команду:");
        scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void inputCommand(String command) {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.handleCommand(command);

    }
}
