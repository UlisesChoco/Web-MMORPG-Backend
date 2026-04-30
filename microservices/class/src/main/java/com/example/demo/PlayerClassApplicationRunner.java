package com.example.demo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.demo.service.jpa.IPlayerClassModifierService;
import com.example.demo.service.jpa.IPlayerClassService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PlayerClassApplicationRunner implements ApplicationRunner {
    private final IPlayerClassService playerClassService;
    
    private final IPlayerClassModifierService playerClassModifierService;

    @Transactional
    @Override
    public void run(org.springframework.boot.ApplicationArguments args) throws Exception {
        playerClassService.saveAllFromResources();

        playerClassModifierService.saveAllFromResources();
    }
}
