package com.example.tikhoretsk_lawyers_bureau_1.database.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUser {
    private String paragraph;
    private Long chatId;
    private List <PaymentDay> paymentDayList;

}
