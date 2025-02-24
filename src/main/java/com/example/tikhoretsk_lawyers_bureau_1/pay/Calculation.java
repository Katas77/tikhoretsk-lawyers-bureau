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

    public String generateResult(long chatId) {
        AppUser appUser = appUserRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (appUser.getParagraph() == null) {
            return "/start";
        }

        String paymentSummary = calculatePayment(appUser.getParagraph(), appUser);
        return String.format("%s%nКоличество дней: %d%nИтого к выплате: %d руб., которую прошу перечислить на банковские реквизиты:%n%n/start",
                paymentSummary, appUser.getPaymentDayList().size(), total);
    }

    private String calculatePayment(String paragraph, AppUser appUser) {
        return switch (paragraph.charAt(0)) {
            case 'a' -> calculateForParagraph(MessageAndDays.day2024[0], MessageAndDays.dayOff2024[0],
                    MessageAndDays.day2025[0], MessageAndDays.dayOff2025[0], appUser);
            case 'b' -> calculateForParagraph(MessageAndDays.day2024[1], MessageAndDays.dayOff2024[1],
                    MessageAndDays.day2025[1], MessageAndDays.dayOff2025[1], appUser);
            case 'v' -> calculateForParagraph(MessageAndDays.day2024[2], MessageAndDays.dayOff2024[2],
                    MessageAndDays.day2025[2], MessageAndDays.dayOff2025[2], appUser);
            case 'g' -> calculateForParagraph(MessageAndDays.day2024[3], MessageAndDays.dayOff2024[3],
                    MessageAndDays.day2025[3], MessageAndDays.dayOff2025[3], appUser);
            default -> null;
        };
    }

    private String calculateForParagraph(int day24, int dayOff24, int day25, int dayOff25, AppUser appUser) {
        total = 0;
        List<PaymentDay> paymentList = appUser.getPaymentDayList();
        LocalDate dateStartOfOctober2024 = LocalDate.of(2024, 10, 1);
        StringBuilder paymentDetails = new StringBuilder();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEEE", new Locale("ru"));

        paymentList.sort(Comparator.comparing(PaymentDay::getDatePay));

        for (PaymentDay payment : paymentList) {
            String formattedDate = dateFormatter.format(payment.getDatePay());
            String dayOfWeek = dayOfWeekFormatter.format(payment.getDatePay().getDayOfWeek());
            int payAmount = calculatePaymentAmount(payment, day24, dayOff24, day25, dayOff25, dateStartOfOctober2024, dayOfWeek);

            total += payAmount;
            paymentDetails.append(String.format("%s - %s - %d руб.%n", formattedDate, dayOfWeek, payAmount));
        }

        log.info("User with chatId {} has total payment of {} ", appUser.getChatId(), total);
        return paymentDetails.toString();
    }

    private int calculatePaymentAmount(PaymentDay payment, int day24, int dayOff24, int day25, int dayOff25, LocalDate dateThreshold, String dayOfWeek) {
        if (payment.getDatePay().isBefore(dateThreshold)) {
            return isWeekendOrHoliday(dayOfWeek, payment) ? dayOff24 : day24;
        } else {
            return isWeekendOrHoliday(dayOfWeek, payment) ? dayOff25 : day25;
        }
    }

    private boolean isWeekendOrHoliday(String dayOfWeek, PaymentDay payment) {
        return dayOfWeek.contains("суббота") || dayOfWeek.contains("воскресенье") ||
                Arrays.stream(MessageAndDays.holidays).anyMatch(day -> payment.getDatePay().isEqual(day));
    }
}
