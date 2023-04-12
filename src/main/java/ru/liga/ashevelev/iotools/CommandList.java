package ru.liga.ashevelev.iotools;

import java.util.List;
import java.util.stream.Collectors;

import static ru.liga.ashevelev.iotools.FileReader.COMMANDS;

/**
 * Класс, который вызывает и печатает список команд для бота.
 */

public class CommandList {
    public String printCommands() {
        FileReader fileReader = new FileReader();
        List<String> lines = fileReader.readCsvLines(COMMANDS);
        return joinStrings(lines);
    }

    private String joinStrings(List<String> list) {
        return list.stream()
                .collect(Collectors.joining("\n"));
    }
}
