package com.example.tikhoretsk_lawyers_bureau_1.pay;

import com.example.tikhoretsk_lawyers_bureau_1.utils.Stats;
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

    public String generateResult(long chatId) {
        AppUser appUser = appUserRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (appUser.getParagraph() == null) {
            return "/start";
        }

        String paymentSummary = calculatePayment( appUser);
        return String.format("%s%nКоличество дней: %d%nИтого к выплате: %d руб., которую прошу перечислить на банковские реквизиты:%n%n/start",
                paymentSummary, appUser.getPaymentDayList().size(), total);
    }


    private String calculatePayment( AppUser appUser) {
        total = 0;
        List<PaymentDay> paymentList = appUser.getPaymentDayList();
        StringBuilder paymentDetails = new StringBuilder();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEEE", new Locale("ru"));

        paymentList.sort(Comparator.comparing(PaymentDay::getDatePay));

        for (PaymentDay payment : paymentList) {
            String formattedDate = dateFormatter.format(payment.getDatePay());
            String dayOfWeek = dayOfWeekFormatter.format(payment.getDatePay().getDayOfWeek());
            int payAmount = dayPay(payment, dayOfWeek,appUser.getParagraph());

            total += payAmount;
            paymentDetails.append(String.format("%s - %s - %d руб.%n", formattedDate, dayOfWeek, payAmount));
        }

        log.info("User with chatId {} has total payment of {} ", appUser.getChatId(), total);
        return paymentDetails.toString();
    }



    private boolean isWeekendOrHoliday(String dayOfWeek, PaymentDay payment) {
        return dayOfWeek.contains("суббота") || dayOfWeek.contains("воскресенье") ||
                Arrays.stream(Stats.holidays).anyMatch(day -> payment.getDatePay().isEqual(day));
    }


    private int dayPay(PaymentDay payment, String dayOfWeek,String paragraph) {
        int yearIdx = getYearIndex(payment.getDatePay());   // 0 -> 2024-range, 1 -> 2025-range, 2 -> 2026-range
        int pIdx = paragraphIndex(paragraph); // 0..3 для а,б,в,г

        int[][] dayArrays = new int[][]{
                Stats.day2024,
                Stats.day2025,
                Stats.day2026
        };
        int[][] dayOffArrays = new int[][]{
                Stats.dayOff2024,
                Stats.dayOff2025,
                Stats.dayOff2026
        };

        boolean holiday = isWeekendOrHoliday(dayOfWeek, payment);

        if (holiday) {
            return dayOffArrays[yearIdx][pIdx];
        } else {
            return dayArrays[yearIdx][pIdx];
        }
    }

    private int getYearIndex(LocalDate date) {
        if (date.isAfter(Stats.RANGE_2024_START) && date.isBefore(Stats.RANGE_2024_END)) return 0;
        if (date.isAfter(Stats.RANGE_2025_START) && date.isBefore(Stats.RANGE_2025_END)) return 1;
        if (date.isAfter(Stats.RANGE_2026_START) && date.isBefore(Stats.RANGE_2026_END)) return 2;
        throw new IllegalArgumentException("Дата вне поддерживаемых диапазонов: " + date);
    }

    private int paragraphIndex(String paragraph) {
        if (paragraph == null || paragraph.isEmpty()) throw new IllegalArgumentException("Параграф пустой");
        char c = Character.toLowerCase(paragraph.charAt(0));
        return switch (c) {
            case 'a' -> 0;
            case 'b' -> 1;
            case 'v' -> 2;
            case 'g' -> 3;
            default -> throw new IllegalArgumentException("Неизвестный параграф: " + paragraph);
        };
    }




}
