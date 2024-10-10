package com.example.tikhoretsk_lawyers_bureau_1.database.repository;

import com.example.tikhoretsk_lawyers_bureau_1.database.model.AppUser;
import com.example.tikhoretsk_lawyers_bureau_1.database.model.PaymentDay;
import com.example.tikhoretsk_lawyers_bureau_1.utils.MessageAndDays;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Repository
@Slf4j
public class AppUserRepository {
    private final ConcurrentHashMap<Long, AppUser> appUsers = new ConcurrentHashMap<>();
    public List<AppUser> findAll(){return (List<AppUser>) appUsers.values();}


    public Optional<AppUser> findByIdAppUser(Long id) {
        return Optional.ofNullable(appUsers.get(id));
    }


    public void save(Long chatId) {
        AppUser appUser = AppUser.builder().chatId(chatId).build();
        if (this.findByIdAppUser(chatId).isEmpty()) {
            appUsers.put(chatId, appUser);
            log.info("User with this chatId {} save", chatId);
        } else {
            log.info("User with this chatId {} isPresent", chatId);
        }

    }


    public void setDay(Long chatId, PaymentDay paymentDay) {
        List<PaymentDay> paymentDays = new ArrayList<>();
        AppUser appUser = this.findByIdAppUser(chatId).orElseThrow();
        if (appUser.getPaymentDayList() == null) {
            appUser.setPaymentDayList(paymentDays);
        } else {
            paymentDays = appUser.getPaymentDayList();
        }
        paymentDays.add(paymentDay);
        appUser.setPaymentDayList(paymentDays);
        appUsers.put(chatId, appUser);

    }

    public void setParagraph(Long chatId, String paragraph) {
        AppUser appUser = this.findByIdAppUser(chatId).orElseThrow();
        appUser.setParagraph(paragraph);
        appUsers.put(chatId, appUser);

    }

    public void newDays(Long chatId) {
        AppUser appUser = this.findByIdAppUser(chatId).orElseThrow();
        List<PaymentDay> paymentDays = new ArrayList<>();
        appUser.setPaymentDayList(paymentDays);
        appUsers.put(chatId, appUser);

    }

    public void newParagraph(Long chatId) {
        AppUser appUser = this.findByIdAppUser(chatId).orElseThrow();
        appUser.setParagraph(null);
        appUsers.put(chatId, appUser);
    }
    @PostConstruct
    public void bureau(){
        Arrays.stream(MessageAndDays.chat_id).forEach(this::save);

    }

    public ArrayList<Long> catIDs(){
        ArrayList<Long> appUsersL=new ArrayList<>();
       for (Map.Entry entry : appUsers.entrySet())
       {AppUser appUser= (AppUser) entry.getValue();
           appUsersL.add(appUser.getChatId());}
   return appUsersL;}

}
