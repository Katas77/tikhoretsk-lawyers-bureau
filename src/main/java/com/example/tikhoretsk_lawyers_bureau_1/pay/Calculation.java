package com.example.tikhoretsk_lawyers_bureau_1.pay;

import com.example.tikhoretsk_lawyers_bureau_1.TextsR;
import com.example.tikhoretsk_lawyers_bureau_1.database.model.AppUser;
import com.example.tikhoretsk_lawyers_bureau_1.database.model.PaymentDay;
import com.example.tikhoretsk_lawyers_bureau_1.database.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.Comparator;

import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class Calculation {
    private final AppUserRepository appUserRepository;
    private  int total;


    public String result(long chat_id) {
        if (appUserRepository.findByIdAppUser(chat_id).orElseThrow().getParagraph()==null)
        {return "";}
        Long id= Long.valueOf(chat_id);
        AppUser appUser = appUserRepository.findByIdAppUser(id).orElseThrow();
        return alla(appUser.getParagraph(), appUser) +
                System.lineSeparator() + "Количество дней " + appUser.getPaymentDayList().size()
                + System.lineSeparator() + "Итого к выплате подлежит сумма  - " + total+ " руб., которую прошу перечислить на банковские реквизиты:";
    }


    String alla(String paragraph, AppUser appUser) {
        if (paragraph.startsWith("а"))
            return wer(TextsR.day2024[0], TextsR.dayOff2024[0], TextsR.day2025[0], TextsR.dayOff2025[0], appUser);
        if (paragraph.startsWith("б"))
            return wer(TextsR.day2024[1], TextsR.dayOff2024[1], TextsR.day2025[1], TextsR.dayOff2025[1], appUser);
        if (paragraph.startsWith("в"))
            return wer(TextsR.day2024[2], TextsR.dayOff2024[2], TextsR.day2025[2], TextsR.dayOff2025[2], appUser);
        if (paragraph.startsWith("г"))
            return wer(TextsR.day2024[3], TextsR.dayOff2024[3], TextsR.day2025[3], TextsR.dayOff2025[3], appUser);
        else return null;
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
                if (valid(sd,payment)) {
                    pay = dayOff24;
                } else pay = day24;
            } else if (valid(sd,payment)) {
                pay = dayOff25;
            } else pay = day25;
            total = total + pay;
            all = all + dayER + " - " + sd + " - " + "  " + pay + "  руб." + System.lineSeparator();
        }

        return all;
    }
    private boolean valid(String sd,PaymentDay payment){
        return sd.contains("суббота") || sd.contains("воскресенье")|| Arrays.stream(TextsR.holidays).anyMatch(day->payment.getDatePay().isEqual(day));
    }

}
