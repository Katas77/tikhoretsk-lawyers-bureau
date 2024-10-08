package com.example.tikhoretsk_lawyers_bureau_1.bot.command;

import com.example.tikhoretsk_lawyers_bureau_1.boards.Boards;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Обработка команды начала работы с ботом
 */
@Service
@Slf4j
@AllArgsConstructor
public class StartCommand implements IBotCommand {
    public final Boards boards;

    @Override
    public String getCommandIdentifier() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Запускает бота";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        var text = "ChatId  %s,  UserName  %s, FirstName  %s, LastName  %s ";
        String formattedText = String.format(text, message.getChatId(), message.getChat().getUserName(), message.getChat().getFirstName(), message.getChat().getLastName());
        log.info(formattedText);
        read(formattedText);
        try {
            absSender.execute(boards.startKeyboardAb(message.getChatId()));
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения", e);
        }
    }

    public void read(String text) {
        StringBuilder allaB = new StringBuilder();
        List<String> allaL = new ArrayList<>();
        try {
            allaL = Files.readAllLines(Paths.get("data/roma.txt"));
            allaL.forEach(al -> {
                allaB.append(al + "\n");
            });
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        List<String> allaL2 = allaL;
        allaL2.add(text);
        try {
            Files.write(Paths.get("data/roma.txt"), allaL2);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

    }

}

