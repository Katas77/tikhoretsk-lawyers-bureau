package com.example.tikhoretsk_lawyers_bureau_1.database.repository;

import com.example.tikhoretsk_lawyers_bureau_1.database.model.AppUser;
import com.example.tikhoretsk_lawyers_bureau_1.database.model.PaymentDay;
import com.example.tikhoretsk_lawyers_bureau_1.utils.MessageAndDays;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
public class AppUserRepository {
    private final ConcurrentHashMap<Long, AppUser> appUsers = new ConcurrentHashMap<>();

    public Optional<AppUser> findById(Long id) {
        return Optional.ofNullable(appUsers.get(id));
    }

    public void save(Long chatId) {
        AppUser appUser = AppUser.builder().chatId(chatId).build();
        AppUser existingUser = appUsers.putIfAbsent(chatId, appUser);

        if (existingUser == null) {
            log.info("User with chatId {} has been saved", chatId);
        } else {
            log.info("User with chatId {} is already present", chatId);
        }
    }

    public void addPaymentDay(Long chatId, PaymentDay paymentDay) {
        AppUser appUser = findById(chatId).orElseThrow(() -> new NoSuchElementException("User not found"));

        List<PaymentDay> paymentDays = Optional.ofNullable(appUser.getPaymentDayList())
                .orElseGet(ArrayList::new);
        paymentDays.add(paymentDay);

        appUser.setPaymentDayList(paymentDays);
        // No need to put back in ConcurrentHashMap unless something changes
    }

    public void updateParagraph(Long chatId, String paragraph) {
        AppUser appUser = findById(chatId).orElseThrow(() -> new NoSuchElementException("User not found"));
        appUser.setParagraph(paragraph);
        // No need to put back in ConcurrentHashMap unless something changes
    }

    public void resetPaymentDays(Long chatId) {
        AppUser appUser = findById(chatId).orElseThrow(() -> new NoSuchElementException("User not found"));
        appUser.setPaymentDayList(new ArrayList<>());
        // No need to put back in ConcurrentHashMap unless something changes
    }

    public void clearParagraph(Long chatId) {
        AppUser appUser = findById(chatId).orElseThrow(() -> new NoSuchElementException("User not found"));
        appUser.setParagraph(null);
        // No need to put back in ConcurrentHashMap unless something changes
    }

    @PostConstruct
    public void initialize() {
        Arrays.stream(MessageAndDays.chat_id)
                .forEach(this::save);
    }

    public List<Long> getChatIds() {
        return new ArrayList<>(appUsers.keySet());
    }
}