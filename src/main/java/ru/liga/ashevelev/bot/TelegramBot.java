package ru.liga.ashevelev.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.ashevelev.iotools.CommandHandler;
import ru.liga.ashevelev.iotools.CommandList;
import ru.liga.ashevelev.iotools.FileReader;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TelegramBot extends TelegramLongPollingBot {
    //todo обычно пишут первым модифкатор досиупа private, должно получиться private final static String ...
    static final private String BOT_TOKEN = "6077329097:AAH5YN96PIlBEk5yiLkRlq_hEov8VfNUbx0";
    static final private String BOT_NAME = "ashevelev_bot";
    private final CommandHandler commandHandler = new CommandHandler();
    private final CommandList commandList = new CommandList();

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {

        String chatId = update.getMessage().getChatId().toString();
        String text = update.getMessage().getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if (text.equals("/start")) {
            sendMessage.setText("Добрый день! Это телеграмм-бот, который может рассчитать курсы валют. Введите /help, чтобы получить список команд.");
        } else if (text.equals("/help")) {
            sendMessage.setText("Список команд:" + "\n" +
                    commandList.printCommands());
        } else {
            sendMessage.setText(commandHandler.handleCommand(text));
        }

        try {
            this.execute(sendMessage);
        } catch (TelegramApiException e) {
            //todo Logger.getLogger(getClass().getName()) надо вынести в приватное поле
            //todo Level.SEVERE встречаю в первый раз, надо использовать ERROR
            // его в в пакете java.util.logging нет, попробуй создать Logger через org.slf4j.LoggerFactory.getLogger
            //todo сообщение должно быть боле информативное, напиример failed to communicate with TelegramApi
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "catch TelegramApiException", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }


}
