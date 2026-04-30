package com.chocolatada.tower;

import com.chocolatada.tower.service.jpa.ITowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TowerApplicationRunner implements CommandLineRunner {
    private final ITowerService towerService;


    @Override
    public void run(String... args) throws Exception {
        towerService.loadFromResources();
    }
}
