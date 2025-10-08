package com.example.tikhoretsk_lawyers_bureau_1.bot;

import com.example.tikhoretsk_lawyers_bureau_1.boards.Boards;
import com.example.tikhoretsk_lawyers_bureau_1.database.model.PaymentDay;
import com.example.tikhoretsk_lawyers_bureau_1.database.repository.AppUserRepository;
import com.example.tikhoretsk_lawyers_bureau_1.pay.Calculation;
import com.example.tikhoretsk_lawyers_bureau_1.utils.Calendar24;
import com.example.tikhoretsk_lawyers_bureau_1.utils.Calendar25;
import com.example.tikhoretsk_lawyers_bureau_1.utils.Calendar26;
import com.example.tikhoretsk_lawyers_bureau_1.utils.Stats;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class BureauBot extends TelegramLongPollingCommandBot {
    private final String botUsername;
    private final Boards boards;
    private final Calculation calculation;
    private final AppUserRepository appUserRepository;

    public BureauBot(
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
    public synchronized void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            handleTextMessage(update);
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update);
        }
    }
    private void handleTextMessage(Update update) {
        long chatId = update.getMessage().getChatId();
        appUserRepository.findById(chatId).orElseGet(() -> {
            appUserRepository.save(chatId);
            return null;
        });

        String messageText = update.getMessage().getText();
        if (!messageText.startsWith("/")) {
            sendMessage(chatId, "У меня сегодня нет настроения общаться на сторонние темы. Нажмите /start");
        } else if (messageText.startsWith("/date_")) {
            parseDate(update.getMessage());
            executeBoardCommand(chatId, boards.nextFinish(chatId));
        }
    }

    private void handleCallbackQuery(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        appUserRepository.findById(chatId).orElseGet(() -> {
            appUserRepository.save(chatId);
            return null;
        });

        String callData = update.getCallbackQuery().getData();
        try {
            switch (callData) {
                case "idea" -> sendMessage(chatId, Stats.help);
                case "hist" -> sendMessage(chatId, Stats.history);
                case "LR" -> executeBoardCommand(chatId, boards.defenders(chatId));
                case "mo" -> sendLawyerContact(chatId, "928 431 72 63");
                case "чу" -> sendLawyerContact(chatId, "918 444 85 13");
                case "за" -> sendLawyerContact(chatId, "928 428 44 15");
                case "па" -> sendLawyerContact(chatId, "918 411 51 96");
                case "чм" -> sendLawyerContact(chatId, "918 941 42 22");
                case "pr" -> sendLawyerContact(chatId, "918 463 33 88");
                case "ка" -> sendLawyerContact(chatId, "906 434 15 78");
                case "де" -> sendLawyerContact(chatId, "918 291 03 68");
                case "кз" -> sendLawyerContact(chatId, "918 119 41 19");
                case "ше" -> sendLawyerContact(chatId, "918 214 03 43");
                case "да" -> sendLawyerContact(chatId, "918 434 83 70");
                case "жд" -> sendLawyerContact(chatId, "918 447 88 53");
                case "тю" -> sendLawyerContact(chatId, "928 427 28 32");
                case "ст" -> sendMessage(chatId, "Контр адмирал скрыл свои данные");
                case "Размер оплаты труда" -> executeBoardCommand(chatId, boards.paragraphs(chatId));
                case "a", "b", "v", "g" -> sendMessage2(chatId, callData);
                case "но" -> executeBoardCommand(chatId, boards.year(chatId));
                case "но2" -> executeBoardCommand(chatId, boards.quarter(chatId));
                case "зкч" -> sendMessageParagraphAndF(chatId, calculation.generateResult(chatId));
                case "quarter_1_24" -> sendMessage(chatId, Calendar24.calendar1Q2024);
                case "quarter_2_24" -> sendMessage(chatId, Calendar24.calendar2Q2024);
                case "quarter_3_24" -> sendMessage(chatId, Calendar24.calendar3Q2024);
                case "quarter_4_24" -> sendMessage(chatId, Calendar24.calendar4Q2024);
                case "quarter_1_25" -> sendMessage(chatId, Calendar25.calendar1Q2025);
                case "quarter_2_25" -> sendMessage(chatId, Calendar25.calendar2Q2025);
                case "quarter_3_25" -> sendMessage(chatId, Calendar25.calendar3Q2025);
                case "quarter_4_25" -> sendMessage(chatId, Calendar25.calendar4Q2025);
                case "quarter_1_26" -> sendMessage(chatId, Calendar26.calendar1Q2026);
                case "quarter_2_26" -> sendMessage(chatId, Calendar26.calendar2Q2026);
                case "quarter_3_26" -> sendMessage(chatId, Calendar26.calendar3Q2026);
            }
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения", e);
        }
    }

    private void sendLawyerContact(Long chatId, String phoneNumber) {
        sendMessage(chatId, "+7 " + phoneNumber);
    }
    private void sendMessage(Long chatId, String text) {
        try {

            SendMessage sendMessage = new SendMessage(String.valueOf(chatId), text);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения в чат с ID {}:   {}", chatId, e.getMessage(), e);
        }
    }

    private void sendMessage2(Long chatId, String paragraph) {
        appUserRepository.resetPaymentDays(chatId);
        appUserRepository.updateParagraph(chatId, paragraph);
        executeBoardCommand(chatId, boards.quarter(chatId));
    }

    public void parseDate(Message message) {
        String date = message.getText().replaceAll("[^0-9]", "");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
        PaymentDay payment = new PaymentDay(LocalDate.parse(date, formatter));
        appUserRepository.addPaymentDay(message.getChatId(), payment);
    }

    private void sendMessageParagraphAndF(Long chatId, String text) throws TelegramApiException {
        if (appUserRepository.findById(chatId).isEmpty() || appUserRepository.findById(chatId).get().getParagraph() == null) {
            sendMessage(chatId, "Начнем с начала /start");
        } else {
            sendMessage(chatId, text);
        }
        appUserRepository.resetPaymentDays(chatId);
        appUserRepository.clearParagraph(chatId);
    }

    @PostConstruct
    private void sendMessageTimer() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::goodMorn, 0, 30, TimeUnit.MINUTES);
    }

    private void goodMorn() {
        if (LocalTime.now().isAfter(LocalTime.of(8, 27)) && LocalTime.now().isBefore(LocalTime.of(9, 0))) {
            String formattedText = String.format("Сегодня %s %s",
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE").localizedBy(new Locale("ru"))));
            for (long id : appUserRepository.getChatIds()) {
                sendMessage(id, Stats.goodMorning() + System.lineSeparator() + formattedText);
            }
        }
    }
    private void executeBoardCommand(Long chatId, SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения", e);
        }
    }
    private void sendPhoto(long chatId) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        InputFile photo = new InputFile(new File("image/8.jpg"));
         sendPhoto .setPhoto(photo);
        try {
           execute(sendPhoto);

        } catch (TelegramApiException e) {
            log.error("Failed to send photo/sticker: {}", e.getMessage());
        }
    }

    }




