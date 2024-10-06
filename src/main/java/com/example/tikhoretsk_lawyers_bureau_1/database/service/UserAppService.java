package com.example.tikhoretsk_lawyers_bureau_1.database.service;


import com.example.tikhoretsk_lawyers_bureau_1.database.repository.AppUserRepository;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@Data
public class UserAppService {

    private final AppUserRepository repository;

    @PostConstruct
    public void setDate() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("The worker");
        }, 0, 2, TimeUnit.MINUTES);
    }


    private void command(Long chatId) {
        String formattedText;
        var text = "Date %s now.";
        formattedText = String.format(text, LocalDate.now());

    }

}
