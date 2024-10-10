package com.example.tikhoretsk_lawyers_bureau_1.pay;

import com.example.tikhoretsk_lawyers_bureau_1.utils.MessageAndDays;
import com.example.tikhoretsk_lawyers_bureau_1.database.model.AppUser;
import com.example.tikhoretsk_lawyers_bureau_1.database.model.PaymentDay;
import com.example.tikhoretsk_lawyers_bureau_1.database.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.Comparator;

import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
public class Calculation {
    private final AppUserRepository appUserRepository;
    private int total;

    public String result(long chat_id) {
        if (appUserRepository.findByIdAppUser(chat_id).isEmpty() || appUserRepository.findByIdAppUser(chat_id).get().getParagraph() == null) {
            return "/start";
        }
        AppUser appUser = appUserRepository.findByIdAppUser(chat_id).orElseThrow();
        return alla(appUser.getParagraph(), appUser) +
                System.lineSeparator() + "Количество дней " + appUser.getPaymentDayList().size()
                + System.lineSeparator() + "Итого к выплате подлежит сумма  - " + total + " руб., которую прошу перечислить на банковские реквизиты:"
                + System.lineSeparator() + System.lineSeparator() + "/start";
    }

    String alla(String paragraph, AppUser appUser) {
        if (paragraph.startsWith("a")) {
            return wer(MessageAndDays.day2024[0], MessageAndDays.dayOff2024[0], MessageAndDays.day2025[0], MessageAndDays.dayOff2025[0], appUser);
        }
        if (paragraph.startsWith("b")) {
            return wer(MessageAndDays.day2024[1], MessageAndDays.dayOff2024[1], MessageAndDays.day2025[1], MessageAndDays.dayOff2025[1], appUser);
        }
        if (paragraph.startsWith("v")) {
            return wer(MessageAndDays.day2024[2], MessageAndDays.dayOff2024[2], MessageAndDays.day2025[2], MessageAndDays.dayOff2025[2], appUser);
        }
        if (paragraph.startsWith("g")) {
            return wer(MessageAndDays.day2024[3], MessageAndDays.dayOff2024[3], MessageAndDays.day2025[3], MessageAndDays.dayOff2025[3], appUser);
        } else return null;
    }

    public String wer(int day24, int dayOff24, int day25, int dayOff25, AppUser appUser) {
        total = 0;
        List<PaymentDay> paymentList = appUser.getPaymentDayList();
        LocalDate date1Oct = LocalDate.of(2024, 10, 1);
        int pay = 0;
        String all = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("EEEE").localizedBy(new Locale("ru"));
        paymentList.sort(Comparator.comparing(PaymentDay::getDatePay));
        for (PaymentDay payment : paymentList) {
            String dayER = formatter.format(payment.getDatePay());
            String sd = formatter2.format(payment.getDatePay().getDayOfWeek());
            if (payment.getDatePay().isBefore(date1Oct)) {
                if (valid(sd, payment)) {
                    pay = dayOff24;
                } else pay = day24;
            } else if (valid(sd, payment)) {
                pay = dayOff25;
            } else pay = day25;
            total = total + pay;
            all = all + dayER + " - " + sd + " - " + "  " + pay + "  руб." + System.lineSeparator();
        }
        log.info("User with this chatId {} total = {} ", appUser.getChatId(), total);
        return all;
    }

    private boolean valid(String sd, PaymentDay payment) {
        return sd.contains("суббота") || sd.contains("воскресенье") || Arrays.stream(MessageAndDays.holidays).anyMatch(day -> payment.getDatePay().isEqual(day));
    }

}
