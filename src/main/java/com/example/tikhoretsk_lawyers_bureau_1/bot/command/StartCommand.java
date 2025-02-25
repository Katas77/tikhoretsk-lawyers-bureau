package com.example.tikhoretsk_lawyers_bureau_1.bot.command;

import com.example.tikhoretsk_lawyers_bureau_1.boards.Boards;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


/**
 * Обработка команды начала работы с ботом
 */

@Service
@Slf4j
@AllArgsConstructor
public class StartCommand implements IBotCommand {
    private final Boards boards;
    private static final String COMMAND_IDENTIFIER = "start";
    private static final String COMMAND_DESCRIPTION = "Запускает бота";
    @Override
    public String getCommandIdentifier() {
        return COMMAND_IDENTIFIER ;
    }

    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] args) {
        logUserDetails(message);

        try {
            absSender.execute(boards.startKeyboardAb(message.getChatId()));
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения", e);
        }
    }

    private void logUserDetails(Message message) {
        String logMessage = String.format("ChatId: %s, UserName: %s, FirstName: %s, LastName: %s",
                message.getChatId(),
                message.getChat().getUserName(),
                message.getChat().getFirstName(),
                message.getChat().getLastName());
        log.error(logMessage);
    }
}


