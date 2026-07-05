package com.chocolatada.loot;

import com.chocolatada.loot.service.jpa.IEnemyItemDropService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LootApplicationRunner implements CommandLineRunner {
    private final IEnemyItemDropService enemyItemDropService;

    @Override
    public void run(String... args) throws Exception {
        enemyItemDropService.loadFromResources();
    }
}
