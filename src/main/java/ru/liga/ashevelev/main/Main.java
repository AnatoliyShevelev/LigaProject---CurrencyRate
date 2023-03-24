package ru.liga.ashevelev.main;

import ru.liga.ashevelev.iotools.ConsoleApplication;

/**
 * допускается введение комманд: "rate try tomorrow", "rate usd tomorrow", "rate euro tomorrow"
 * или "rate try week", "rate usd week", "rate euro week"
 */

public class Main {
    public static void main(String[] args) {
        ConsoleApplication run = new ConsoleApplication();
        run.start();
    }
}
