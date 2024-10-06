package com.example.tikhoretsk_lawyers_bureau_1.bot.command;

import com.example.tikhoretsk_lawyers_bureau_1.boards.Boards;
import com.example.tikhoretsk_lawyers_bureau_1.database.model.PaymentDay;
import com.example.tikhoretsk_lawyers_bureau_1.database.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;

@Service
@Slf4j
@AllArgsConstructor
public class DateCommand implements IBotCommand {

 private final AppUserRepository appUserRepository;
   private final Boards boards;

    @Override
    public String getCommandIdentifier() {
        return "date";
    }

    @Override
    public String getDescription() {
        return "расчет";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        qwe(message);
        try {
            absSender.execute( boards.nextFinish(message.getChatId()));
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения", e);
        }

    }
    private void sendMessage(AbsSender absSender, Message message, String text) {
        var chatIdStr = String.valueOf(message.getChatId());
        var sendMessage = new SendMessage(chatIdStr, text);
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения", e);
        }
    }
    public void qwe(Message message){
        String date = message.getText().replaceAll("[^0-9]", "");
        System.out.println(date);
        String[] arrayDate = date.split("");
        String yearSt = "20" + arrayDate[4] + arrayDate[5];
        int year = Integer.parseInt(yearSt);
        String monthSt = arrayDate[2] + arrayDate[3];
        int month = Integer.parseInt(monthSt);
        String daySt = arrayDate[0] + arrayDate[1];
        int day = Integer.parseInt(daySt);
        PaymentDay payment = new PaymentDay( LocalDate.of(year, month, day));
        appUserRepository.setDay(message.getChatId(),payment);
    }
}
