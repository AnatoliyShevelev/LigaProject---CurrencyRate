package ru.liga.ashevelev.iotools;

import java.util.List;

import static ru.liga.ashevelev.iotools.FileReader.COMMANDS;

/**
 * Класс, который вызывает и печатает список команд для бота.
 */

public class CommandList {
    public String printCommands() {
        FileReader fileReader = new FileReader();
        CommandList commandList = new CommandList();
        List<String> lines = fileReader.readCsvLines(COMMANDS);
        return commandList.joinStrings(lines);
    }

    private String joinStrings(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }
}
