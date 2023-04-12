package ru.liga.ashevelev.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.ashevelev.iotools.CommandHandler;
import ru.liga.ashevelev.iotools.CommandList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TelegramBot extends TelegramLongPollingBot {
    private static final String BOT_TOKEN = "6077329097:AAH5YN96PIlBEk5yiLkRlq_hEov8VfNUbx0";
    private static final String BOT_NAME = "ashevelev_bot";
    private final CommandHandler commandHandler = new CommandHandler();
    private final CommandList commandList = new CommandList();
   private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }


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
            logger.error("failed to communicate with TelegramApi", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }


}
