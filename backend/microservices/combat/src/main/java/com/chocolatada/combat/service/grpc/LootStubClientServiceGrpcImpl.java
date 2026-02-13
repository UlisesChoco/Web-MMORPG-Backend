package com.chocolatada.combat.service.grpc;

import com.chocolatada.combat.domain.Loot;
import com.chocolatada.combat.grpc.*;
import com.chocolatada.combat.mapper.LootMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LootStubClientServiceGrpcImpl {
    private final LootServiceGrpc.LootServiceBlockingStub lootStub;

    public LootGrpc roll(Enemy enemyGrpc) {
        RollEnemyLootRequest request = RollEnemyLootRequest.newBuilder()
                .setEnemyId((int) enemyGrpc.getId())
                .build();

        RollEnemyLootResponse response = lootStub.rollEnemyLoot(request);

        Long itemId = null;

        if(response.getDropped())
            itemId = response.getItemId();

        Loot loot = Loot.builder()
                .gold(enemyGrpc.getGold())
                .itemId(itemId)
                .build();

        return LootMapper.toLootGrpc(loot, enemyGrpc.getExperience());
    }
}
