package com.chocolatada.loot.service.jpa;

import com.chocolatada.loot.dto.EnemyItemDropDTO;
import com.chocolatada.loot.dto.LootDTO;

import java.util.List;

public interface IEnemyItemDropService {
    List<EnemyItemDropDTO> findEnemyDropTableByEnemyId(Long enemyId);

    LootDTO rollEnemyLoot(Long enemyId);

    void loadFromResources();
}
