package com.chocolatada.loot.service.jpa.impl;

import com.chocolatada.loot.configuration.resources.EnemyItemDropResources;
import com.chocolatada.loot.dto.EnemyItemDropDTO;
import com.chocolatada.loot.dto.LootDTO;
import com.chocolatada.loot.entity.EnemyItemDropEntity;
import com.chocolatada.loot.mapper.EnemyItemDropMapper;
import com.chocolatada.loot.repository.IEnemyItemDropRepository;
import com.chocolatada.loot.service.jpa.IEnemyItemDropService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnemyItemDropServiceImpl implements IEnemyItemDropService {
    private final IEnemyItemDropRepository enemyItemDropRepository;

    private final EnemyItemDropResources resources;

    @Override
    public List<EnemyItemDropDTO> findEnemyDropTableByEnemyId(Long enemyId) {
        List<EnemyItemDropEntity> entities = enemyItemDropRepository.findByEnemyId(enemyId);

        return EnemyItemDropMapper.toEnemyItemDropDTOs(entities);
    }

    /*
    temporal, simula la tirada de loot comparando el valor de la tirada contra
    la probabilidad de dropeo de cada item.
    mas adelante cambiar esto por un sistema de dropeo mas complejo
     */
    @Override
    public LootDTO rollEnemyLoot(Long enemyId) {
        List<EnemyItemDropEntity> dropTable = enemyItemDropRepository.findByEnemyId(enemyId);

        boolean dropped = false;
        LootDTO loot = new LootDTO(false);
        int i = 0;

        while(!dropped && i < dropTable.size()) {
            EnemyItemDropEntity drop = dropTable.get(i);
            float roll = (float) Math.random();
            if (roll <= drop.getProbability()) {
                dropped = true;
                loot.setDropped(true);
                loot.setItemId(drop.getItemId());
            }
            i++;
        }

        return loot;
    }

    @Override
    @Transactional
    public void loadFromResources() {
        List<EnemyItemDropEntity> entities = EnemyItemDropMapper.toEnemyItemDropEntities(resources.getEnemyItemDrops());

        enemyItemDropRepository.saveAll(entities);
    }
}
