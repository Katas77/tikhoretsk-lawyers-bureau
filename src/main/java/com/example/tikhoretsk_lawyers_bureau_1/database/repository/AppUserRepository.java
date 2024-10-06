package com.example.tikhoretsk_lawyers_bureau_1.database.repository;

import com.example.tikhoretsk_lawyers_bureau_1.database.model.AppUser;
import com.example.tikhoretsk_lawyers_bureau_1.database.model.PaymentDay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Repository
@Slf4j
public class AppUserRepository {
   private final ConcurrentHashMap<Long, AppUser> appUsers = new ConcurrentHashMap<>();



    public List<AppUser> findAll() {
        log.debug("Call findAll in InMemoryTaskRepository");
        return (List<AppUser>) appUsers.values();
    }


    public Optional<AppUser> findByIdAppUser(Long id) {
        log.debug("Call findById in InMemoryTaskRepository. ID is: {}", id);
        return Optional.ofNullable(appUsers.get(id));
    }



    public void save(Long chatId) {
        AppUser appUser = AppUser.builder().chatId(chatId).build();
        if (!this.findByIdAppUser(chatId).isPresent()) {
            appUsers.put(chatId, appUser);
            System.out.println(appUser.getChatId());
            System.out.println(appUsers.size());
        } else {
            log.error("Юзер с таким chatId {} isPresent", chatId);
        }

    }


    public void setDay(Long chatId,PaymentDay paymentDay) {

        List<PaymentDay> paymentDays=new ArrayList<>();
        AppUser appUser= this.findByIdAppUser(chatId).get();
        if (appUser.getPaymentDayList()==null){
         appUser.setPaymentDayList(paymentDays);}
        else {paymentDays=appUser.getPaymentDayList();}
        System.out.println(appUser.getPaymentDayList().size());
        paymentDays.add(paymentDay);
        appUser.setPaymentDayList(paymentDays);
        appUsers.put(chatId,appUser);

    }


    public void setParagraph(Long chatId,String paragraph) {

        AppUser appUser= this.findByIdAppUser(chatId).get();
      appUser.setParagraph(paragraph);
        appUsers.put(chatId,appUser);

    }

    public void newtDays(Long chatId) {
        AppUser appUser= this.findByIdAppUser(chatId).get();
        List<PaymentDay> paymentDays=new ArrayList<>();
        appUser.setPaymentDayList(paymentDays);
        appUsers.put(chatId,appUser);

    }



}
