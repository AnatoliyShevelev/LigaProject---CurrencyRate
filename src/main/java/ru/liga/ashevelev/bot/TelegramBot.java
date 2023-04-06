package ru.liga.ashevelev.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.ashevelev.iotools.CommandHandler;

public class TelegramBot extends TelegramLongPollingBot {
    //todo можно добавить модификатор static
    final private String BOT_TOKEN = "6077329097:AAH5YN96PIlBEk5yiLkRlq_hEov8VfNUbx0";
    final private String BOT_NAME = "ashevelev_bot";
    private final CommandHandler commandHandler = new CommandHandler();

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
        sendMessage.setText(commandHandler.handleCommand(text));

        try {
            this.execute(sendMessage);
        } catch (TelegramApiException e) {
           // e.printStackTrace();
            //todo когда ловишь еxception пиши лог в консоль, о том что случилось
            //todo минимальная конструкция для еxception ->  throw  new RuntimeException(e.getMessage(), e)
            throw  new RuntimeException(e);
        }
    }
}
