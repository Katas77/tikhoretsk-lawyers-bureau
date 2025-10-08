package com.example.tikhoretsk_lawyers_bureau_1.bot.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@Slf4j
public class StickerCommand implements IBotCommand {

    private static final String COMMAND_IDENTIFIER = "stick";
    private static final String COMMAND_DESCRIPTION = "Запускает stick";
    private static final String PHOTO_URL = "https://9001545.ru/img/%D0%B2%D1%8B%D0%B1%D1%80%D0%B0%D1%82%D1%8C-%D0%BB%D1%83%D1%87%D1%88%D0%B5%D0%B3%D0%BE-%D0%B0%D0%B4%D0%B2%D0%BE%D0%BA%D0%B0%D1%82%D0%B0.jpg";
    private static final String STICKER_ID = "CAACAgIAAxkBAAIcIGe8QDBGVYgx5QdpBAQFgB9EIppOAAIbAAPANk8Tfb2Kg8tETWo2BA";
    private static final String STICKER_ID_PASTER = "CAACAgIAAxkBAAIczWe9ilL5zxPqAiMB3h9V3VqgwxpmAAIOFgACOezQSg1dA4vIGTVkNgQ";
    private static final String STICKER_ID_JIM = "CAADBQADiQMAAukKyAPZH7wCI2BwFxYE";


    @Override
    public String getCommandIdentifier() {
        return COMMAND_IDENTIFIER;
    }

    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] args) {
        sendFormattedMessage(message.getChatId(), absSender);
        sendSticker(message.getChatId(), absSender);
    }

    private void sendSticker(long chatId, AbsSender absSender) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(PHOTO_URL));
        //InputFile photo = new InputFile(new File("image/3.jpg"));
        // sendPhoto .setPhoto(photo);
        SendSticker sticker = new SendSticker();
        sticker.setChatId(chatId);
        sticker.setSticker(new InputFile(STICKER_ID_PASTER));

        try {
            absSender.execute(sendPhoto);
            absSender.execute(sticker);
        } catch (TelegramApiException e) {
            log.error("Failed to send photo/sticker: {}", e.getMessage());
        }
    }

    private void sendFormattedMessage(long chatId, AbsSender absSender) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setParseMode(ParseMode.HTML);
        message.setText("<b>Привет!</b> Это <i>красивое</i> сообщение!");

        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            log.error("Failed to send formatted message: {}", e.getMessage());
        }
    }

    private void getStickerId(Update update) {
        Message message = update.getMessage();
        Sticker sticker = message.getSticker();
        if (sticker != null) {
            System.out.println(sticker);// id Sticker
        }
    }

}
