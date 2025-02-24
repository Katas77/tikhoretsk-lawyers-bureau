package com.example.tikhoretsk_lawyers_bureau_1.bot.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Service
@Slf4j
public class DellCommand implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return "dell";
    }

    @Override
    public String getDescription() {
        return "удаляет сообщение";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        deleteMessage(message.getChatId(), message.getMessageId(), absSender);
    }

    public void deleteMessage(long chatId, int messageId, AbsSender absSender) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(String.valueOf(chatId));
        deleteMessage.setMessageId(messageId);
        try {
            absSender.execute(deleteMessage);
            log.info("Message with ID {} has been deleted.", messageId);
        } catch (Exception e) {
            log.error("Failed to delete message: {}", e.getMessage());
        }
    }

}
