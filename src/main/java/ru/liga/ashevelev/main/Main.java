package ru.liga.ashevelev.main;

import ru.liga.ashevelev.iotools.RunningProgram;

/**
 * допускается введение комманд: "rate try tomorrow", "rate usd tomorrow", "rate euro tomorrow"
 * или "rate try week", "rate usd week", "rate euro week"
 */

public class Main {
    public static void main(String[] args) {
        RunningProgram run = new RunningProgram();
        String command = run.readCommand();
        run.inputCommand(command);
    }
}
