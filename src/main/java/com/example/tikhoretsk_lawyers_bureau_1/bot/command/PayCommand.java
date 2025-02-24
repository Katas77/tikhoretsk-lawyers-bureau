package com.example.tikhoretsk_lawyers_bureau_1.bot.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class PayCommand implements IBotCommand {

    @Override
    public String getCommandIdentifier() {
        return "pay";
    }

    @Override
    public String getDescription() {
        return "оплата";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        sendPaymentButton(message.getChatId(), absSender);

    }
    private void sendPaymentButton(long chatId, AbsSender absSender) {
        List<InlineKeyboardButton> paymentButton = createButtonRow();
        SendMessage message = messageGreat("Нажмите на кнопку, чтобы произвести оплату:", paymentButton, chatId);
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            System.err.println(e.getMessage());
        }
    }

    private List<InlineKeyboardButton> createButtonRow() {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Оплатить");
        button.setCallbackData("PAYMENT");
        return Collections.singletonList(button);
    }


    public SendMessage messageGreat(String text, List<InlineKeyboardButton> rowsInline, long chat_id) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(Collections.singletonList(rowsInline));
        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText(text);
        message.setParseMode(ParseMode.HTML);
        message.setReplyMarkup(markupInline);
        return message;
    }
}
/**
private void createInvoice(long chatId) {
    SendInvoice invoice = new SendInvoice();
    invoice.setChatId(String.valueOf(chatId));
    invoice.setTitle("Оплата товара");
    invoice.setDescription("Описание товара для оплаты.");
    invoice.setPayload("Unique_Payload"); // Уникальный идентификатор товара
    invoice.setProviderToken("YourProviderToken"); // Токен вашего провайдера платежа
    invoice.setStartParameter("start"); // Уникальный параметр для запуска
    invoice.setCurrency("RUB"); // Валюта
    invoice.setPrices(List.of(new LabeledPrice("Товар", 10000))); // Список цен (100.00 RUB)

    try {
        execute(invoice);
    } catch (TelegramApiException e) {
        e.printStackTrace();
    }
}*/
