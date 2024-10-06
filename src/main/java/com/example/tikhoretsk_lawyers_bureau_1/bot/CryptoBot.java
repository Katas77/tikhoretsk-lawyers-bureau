package com.example.tikhoretsk_lawyers_bureau_1.bot;

import com.example.tikhoretsk_lawyers_bureau_1.TextsR;
import com.example.tikhoretsk_lawyers_bureau_1.boards.Boards;
import com.example.tikhoretsk_lawyers_bureau_1.database.repository.AppUserRepository;
import com.example.tikhoretsk_lawyers_bureau_1.pay.Calculation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class CryptoBot extends TelegramLongPollingCommandBot {
    private final String botUsername;
    private final Boards boards;
    private final Calculation calculation;
    private final AppUserRepository appUserRepository;


    public CryptoBot(
            @Value("${telegram.bot.token}") String botToken,
            @Value("${telegram.bot.username}") String botUsername,
            List<IBotCommand> commandList, Boards boards, Calculation calculation, AppUserRepository appUserRepository
    ) {
        super(botToken);
        this.botUsername = botUsername;
        this.boards = boards;
        this.calculation = calculation;
        this.appUserRepository = appUserRepository;
        commandList.forEach(this::register);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void processNonCommandUpdate(Update update) {


        if ((update.hasMessage() && update.getMessage().hasText())) {

            String message_text = update.getMessage().getText();
            if (!(Objects.equals(message_text, "/start")||message_text.startsWith("/date")))
                sendMessage(update.getMessage().getChatId(), "У меня сегодня нет настроения общаться на  сторонние  темы.  Нажмите /start ");
        }

        if (!(update.hasMessage() && update.getMessage().hasText())) {
            String call_data = update.getCallbackQuery().getData();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();
            try {


                switch (call_data) {
                    case "idea" -> sendMessage(chat_id, TextsR.help);

                    case "hist"-> sendMessage(chat_id, TextsR.history);

                    case "LR" -> execute(boards.defenders(chat_id));

                    case "moroz" -> sendMessage(chat_id, "+7 928 431-72-63");
                    case "чу" -> sendMessage(chat_id, "+7 918 444-85-13");
                    case "ст" -> sendMessage(chat_id, "Контр адмирал скрыл свои данные");
                    case "за" -> sendMessage(chat_id, "+7 928 428-44-15");
                    case "па" -> sendMessage(chat_id, "+7 918 411-51-96");
                    case "чм" -> sendMessage(chat_id, "+7 918 941-42-22");
                    case "pro" -> sendMessage(chat_id, "+7 918 463-33-88");
                    case "ка" -> sendMessage(chat_id, "+7 906 434-15-78");
                    case "де" -> sendMessage(chat_id, "+7 918 291-03-68");
                    case "каз" -> sendMessage(chat_id, "+7 918 119-41-19");
                    case "ше" -> sendMessage(chat_id, "+7 918 214-03-43");
                    case "да" -> sendMessage(chat_id, "+7 965 466-46-00");
                    case "жд" -> sendMessage(chat_id, "+7 918 447-88-53");
                    case "тю" -> sendMessage(chat_id, "+7 928 427-28-32");
                    case "кат" -> sendMessage(chat_id, "Писал этого бота две недели на Java.");

                    case "Размер оплаты труда" -> execute(boards.paragraphs(chat_id));
                    case "А" -> sendMessage(chat_id, TextsR.dateText,"а");
                    case "Б" -> sendMessage(chat_id, TextsR.dateText,"б");
                    case "В" -> sendMessage(chat_id, TextsR.dateText,"в");
                    case "Г" -> sendMessage(chat_id, TextsR.dateText,"г");

                    case "но" -> sendMessage(chat_id, TextsR.dateText);
                    case "законч" -> sendMessage(chat_id,calculation.result(chat_id));


                }
            } catch (TelegramApiException e) {
                log.error("Ошибка отправки сообщения", e);
            }

        }
    }


    private void sendMessage(Long chatId, String text) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения", e);
        }
    }
    private void sendMessage(Long chatId, String text,String paragraph) {
        appUserRepository.setParagraph(chatId,paragraph);
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения", e);
        }
    }

}
