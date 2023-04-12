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
        //todo не надо создавать еще один объект класса в уже созданном объекте
        // нужно просто вызвать joinStrings()
        CommandList commandList = new CommandList();
        List<String> lines = fileReader.readCsvLines(COMMANDS);
        return commandList.joinStrings(lines);
    }

    private String joinStrings(List<String> list) {
        //todo это можно сделать через stream
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }
}
