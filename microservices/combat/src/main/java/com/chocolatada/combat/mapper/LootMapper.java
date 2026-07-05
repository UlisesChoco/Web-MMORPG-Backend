package com.chocolatada.combat.mapper;

import com.chocolatada.combat.domain.Loot;
import com.chocolatada.combat.grpc.LootGrpc;

public class LootMapper {
    public static LootGrpc toLootGrpc(Loot loot, int experience) {
        LootGrpc.Builder builder = LootGrpc.newBuilder()
                .setGold(loot.getGold())
                .setExperience(experience);

        if (loot.getItemId() != null) {
            builder.setItemId(loot.getItemId());
        }

        return builder.build();
    }
}
