package com.example.tikhoretsk_lawyers_bureau_1.bot;

import com.example.tikhoretsk_lawyers_bureau_1.utils.Calendar24;
import com.example.tikhoretsk_lawyers_bureau_1.utils.Calendar25;
import com.example.tikhoretsk_lawyers_bureau_1.utils.MessageAndDays;
import com.example.tikhoretsk_lawyers_bureau_1.boards.Boards;
import com.example.tikhoretsk_lawyers_bureau_1.database.model.PaymentDay;
import com.example.tikhoretsk_lawyers_bureau_1.database.repository.AppUserRepository;
import com.example.tikhoretsk_lawyers_bureau_1.pay.Calculation;
import jakarta.annotation.PostConstruct;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
        System.out.println(botToken);
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
        appUserRepository.findByIdAppUser(chatId).orElseGet(() -> {
            appUserRepository.save(chatId);
            return null;
        });

        String messageText = update.getMessage().getText();
        if (!messageText.equals("/start") && !messageText.startsWith("/date_")) {
            sendMessage(chatId, "У меня сегодня нет настроения общаться на сторонние темы. Нажмите /start");
        } else if (messageText.startsWith("/date_")) {
            parseDate(update.getMessage());
            executeBoardCommand(chatId, boards.nextFinish(chatId));
        }
    }

    private void handleCallbackQuery(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        appUserRepository.findByIdAppUser(chatId).orElseGet(() -> {
            appUserRepository.save(chatId);
            return null;
        });

        String callData = update.getCallbackQuery().getData();
        try {
            switch (callData) {
                case "idea" -> sendMessage(chatId, MessageAndDays.help);
                case "hist" -> sendMessage(chatId, MessageAndDays.history);
                case "LR" -> executeBoardCommand(chatId, boards.defenders(chatId));
                case "mo" -> sendLawyerContact(chatId, "9284317263");
                case "чу" -> sendLawyerContact(chatId, "9184448513");
                case "за" -> sendLawyerContact(chatId, "9284284415");
                case "па" -> sendLawyerContact(chatId, "9184115196");
                case "чм" -> sendLawyerContact(chatId, "9189414222");
                case "pr" -> sendLawyerContact(chatId, "9184633388");
                case "ка" -> sendLawyerContact(chatId, "9064341578");
                case "де" -> sendLawyerContact(chatId, "9182910368");
                case "кз" -> sendLawyerContact(chatId, "9181194119");
                case "ше" -> sendLawyerContact(chatId, "9182140343");
                case "да" -> sendLawyerContact(chatId, "9654664600");
                case "жд" -> sendLawyerContact(chatId, "9184478853");
                case "тю" -> sendLawyerContact(chatId, "9284272832");
                case "ст" -> sendMessage(chatId, "Контр адмирал скрыл свои данные");
                case "Размер оплаты труда" -> executeBoardCommand(chatId, boards.paragraphs(chatId));
                case "a", "b", "v", "g" -> sendMessage2(chatId, callData);
                case "но" -> executeBoardCommand(chatId, boards.quarter(chatId));
                case "законч" -> sendMessageParagraphAndF(chatId, calculation.generateResult(chatId));
                case "quarter_1_24" -> sendMessage(chatId, Calendar24.calendar1Q2024);
                case "quarter_2_24" -> sendMessage(chatId, Calendar24.calendar2Q2024);
                case "quarter_3_24" -> sendMessage(chatId, Calendar24.calendar3Q2024);
                case "quarter_4_24" -> sendMessage(chatId, Calendar24.calendar4Q2024);
                case "quarter_1_25" -> sendMessage(chatId, Calendar25.calendar1Q2025);
                case "quarter_2_25" -> sendMessage(chatId, Calendar25.calendar2Q2025);
                case "quarter_3_25" -> sendMessage(chatId, Calendar25.calendar3Q2025);
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
            var sendMessage = new SendMessage(String.valueOf(chatId), text);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения", e);
        }
    }

    private void sendMessage2(Long chatId, String paragraph) {
        appUserRepository.newDays(chatId);
        appUserRepository.setParagraph(chatId, paragraph);
        executeBoardCommand(chatId, boards.quarter(chatId));
    }

    public void parseDate(Message message) {
        String date = message.getText().replaceAll("[^0-9]", "");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
        PaymentDay payment = new PaymentDay(LocalDate.parse(date, formatter));
        appUserRepository.setDay(message.getChatId(), payment);
    }

    private void sendMessageParagraphAndF(Long chatId, String text) throws TelegramApiException {
        if (appUserRepository.findByIdAppUser(chatId).isEmpty() || appUserRepository.findByIdAppUser(chatId).get().getParagraph() == null) {
            sendMessage(chatId, "Начнем с начала /start");
        } else {
            sendMessage(chatId, text);
        }
        appUserRepository.newDays(chatId);
        appUserRepository.newParagraph(chatId);
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

            for (long id : appUserRepository.catIDs()) {
                sendMessage(id, MessageAndDays.goodMorning() + System.lineSeparator() + formattedText);
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
}

