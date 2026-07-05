package com.chocolatada.playerclass;

import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.chocolatada.playerclass.service.jpa.IPlayerClassModifierService;
import com.chocolatada.playerclass.service.jpa.IPlayerClassService;

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
