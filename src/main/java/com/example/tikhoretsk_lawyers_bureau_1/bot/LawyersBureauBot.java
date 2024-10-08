package com.example.tikhoretsk_lawyers_bureau_1.bot;

import com.example.tikhoretsk_lawyers_bureau_1.utils.Calendar24;
import com.example.tikhoretsk_lawyers_bureau_1.utils.Calendar25;
import com.example.tikhoretsk_lawyers_bureau_1.utils.MessageAndDays;
import com.example.tikhoretsk_lawyers_bureau_1.boards.Boards;
import com.example.tikhoretsk_lawyers_bureau_1.database.model.PaymentDay;
import com.example.tikhoretsk_lawyers_bureau_1.database.repository.AppUserRepository;
import com.example.tikhoretsk_lawyers_bureau_1.pay.Calculation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Service
@Slf4j
public class LawyersBureauBot extends TelegramLongPollingCommandBot {
    private final String botUsername;
    private final Boards boards;
    private final Calculation calculation;
    private final AppUserRepository appUserRepository;


    public LawyersBureauBot(
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
            long id = update.getMessage().getChatId();
            if (appUserRepository.findByIdAppUser(id).isEmpty()) {
                appUserRepository.save(id);
            }
            String message_text = update.getMessage().getText();
            if (!(Objects.equals(message_text, "/start") || message_text.startsWith("/date_")))
                sendMessage(id, "У меня сегодня нет настроения общаться на  сторонние  темы.  Нажмите /start ");
            else if (message_text.startsWith("/date_")) {
                parseDate(update.getMessage());
                try {
                    execute(boards.nextFinish(id));
                } catch (TelegramApiException e) {
                    log.error("Ошибка отправки сообщения", e);
                }
            }
        }

        if (!(update.hasMessage() && update.getMessage().hasText())) {
            log.info(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
            String call_data = update.getCallbackQuery().getData();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();
            if (appUserRepository.findByIdAppUser(chat_id).isEmpty()) {
                appUserRepository.save(chat_id);
            }
            try {


                switch (call_data) {
                    case "idea" -> sendMessage(chat_id, MessageAndDays.help);
                    case "hist" -> sendMessage(chat_id, MessageAndDays.history);
                    case "LR" -> execute(boards.defenders(chat_id));

                    case "mo" -> sendMessage(chat_id, "+7 928 431-72-63");
                    case "чу" -> sendMessage(chat_id, "+7 918 444-85-13");
                    case "за" -> sendMessage(chat_id, "+7 928 428-44-15");
                    case "па" -> sendMessage(chat_id, "+7 918 411-51-96");
                    case "чм" -> sendMessage(chat_id, "+7 918 941-42-22");
                    case "pr" -> sendMessage(chat_id, "+7 918 463-33-88");
                    case "ка" -> sendMessage(chat_id, "+7 906 434-15-78");
                    case "де" -> sendMessage(chat_id, "+7 918 291-03-68");
                    case "кз" -> sendMessage(chat_id, "+7 918 119-41-19");
                    case "ше" -> sendMessage(chat_id, "+7 918 214-03-43");
                    case "да" -> sendMessage(chat_id, "+7 965 466-46-00");
                    case "жд" -> sendMessage(chat_id, "+7 918 447-88-53");
                    case "тю" -> sendMessage(chat_id, "+7 928 427-28-32");
                    case "ст" -> sendMessage(chat_id, "Контр адмирал скрыл свои данные");


                    case "Размер оплаты труда" -> execute(boards.paragraphs(chat_id));

                    case "a" -> sendMessage2(chat_id, "a");
                    case "b" -> sendMessage2(chat_id, "b");
                    case "v" -> sendMessage2(chat_id, "v");
                    case "g" -> sendMessage2(chat_id, "g");

                    case "но" -> execute(boards.quarter(chat_id));
                    case "законч" -> sendMessageParagraphAndF(chat_id, calculation.result(chat_id));

                    case "quarter_1_24" -> sendMessage(chat_id, Calendar24.calendar1Q2024);
                    case "quarter_2_24" -> sendMessage(chat_id, Calendar24.calendar2Q2024);
                    case "quarter_3_24" -> sendMessage(chat_id, Calendar24.calendar3Q2024);
                    case "quarter_4_24" -> sendMessage(chat_id, Calendar24.calendar4Q2024);
                    case "quarter_1_25" -> sendMessage(chat_id, Calendar25.calendar1Q2025);
                    case "quarter_2_25" -> sendMessage(chat_id, Calendar25.calendar2Q2025);
                    case "quarter_3_25" -> sendMessage(chat_id, Calendar25.calendar3Q2025);

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

    private void sendMessage2(Long chatId, String paragraph) {
        appUserRepository.newDays(chatId);
        appUserRepository.setParagraph(chatId, paragraph);
        try {
            execute(boards.quarter(chatId));
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения", e);
        }
    }

    public void parseDate(Message message) {
        String date = message.getText().replaceAll("[^0-9]", "");
        String[] arrayDate = date.split("");
        String yearSt = "20" + arrayDate[4] + arrayDate[5];
        int year = Integer.parseInt(yearSt);
        String monthSt = arrayDate[2] + arrayDate[3];
        int month = Integer.parseInt(monthSt);
        String daySt = arrayDate[0] + arrayDate[1];
        int day = Integer.parseInt(daySt);
        PaymentDay payment = new PaymentDay(LocalDate.of(year, month, day));
        appUserRepository.setDay(message.getChatId(), payment);
    }

    private void sendMessageParagraphAndF(Long chatId, String text) throws TelegramApiException {
        var chatIdStr = String.valueOf(chatId);
        var message = new SendMessage(chatIdStr, text);
        if (appUserRepository.findByIdAppUser(chatId).isEmpty() || appUserRepository.findByIdAppUser(chatId).get().getParagraph() == null) {
            sendMessage(chatId, "Начнем с начала /start");
        } else
            execute(message);
        appUserRepository.newDays(chatId);
        appUserRepository.newParagraph(chatId);

    }

    private void sendMessageTimer() {
        this.valid();
      /*  ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::valid, 0, 10, TimeUnit.MINUTES);*/
    }

    private void valid() {
        var formattedText = MessageAndDays.messageTe[1];
        for (long id : MessageAndDays.chat_id) {
            sendMessage(id, formattedText);
        }
    }
}


