package com.chocolatada.loot.mapper;

import com.chocolatada.loot.configuration.resources.EnemyItemDropResource;
import com.chocolatada.loot.dto.EnemyItemDropDTO;
import com.chocolatada.loot.entity.EnemyItemDropEntity;

import java.util.ArrayList;
import java.util.List;

public class EnemyItemDropMapper {
    public static EnemyItemDropDTO toEnemyItemDropDTO(EnemyItemDropEntity entity) {
        return EnemyItemDropDTO.builder()
                .itemId(entity.getItemId())
                .probability(entity.getProbability())
                .build();
    }

    public static List<EnemyItemDropDTO> toEnemyItemDropDTOs(List<EnemyItemDropEntity> entities) {
        List<EnemyItemDropDTO> dtos = new ArrayList<>();

        for(EnemyItemDropEntity entity : entities)
            dtos.add(toEnemyItemDropDTO(entity));

        return dtos;
    }

    public static EnemyItemDropEntity toEnemyItemDropEntity(EnemyItemDropResource resource) {
        return EnemyItemDropEntity.builder()
                .enemyId(resource.getEnemyId())
                .itemId(resource.getItemId())
                .probability(resource.getProbability())
                .build();
    }

    public static List<EnemyItemDropEntity> toEnemyItemDropEntities(List<EnemyItemDropResource> enemyItemDrops) {
        List<EnemyItemDropEntity> entities = new ArrayList<>();

        for(EnemyItemDropResource resource : enemyItemDrops)
            entities.add(toEnemyItemDropEntity(resource));

        return entities;
    }

    public static com.chocolatada.loot.grpc.LootItem toLootItem(EnemyItemDropDTO dto) {
        return com.chocolatada.loot.grpc.LootItem.newBuilder()
                .setItemId(dto.getItemId().intValue())
                .setDropChance(dto.getProbability())
                .build();
    }

    public static List<com.chocolatada.loot.grpc.LootItem> toLootItems(List<EnemyItemDropDTO> dtos) {
        List<com.chocolatada.loot.grpc.LootItem> lootItems = new ArrayList<>();

        for(EnemyItemDropDTO dto : dtos)
            lootItems.add(toLootItem(dto));

        return lootItems;
    }

    public static com.chocolatada.loot.grpc.RollEnemyLootResponse toRollEnemyLootResponse(com.chocolatada.loot.dto.LootDTO dto) {
        com.chocolatada.loot.grpc.RollEnemyLootResponse.Builder responseBuilder = com.chocolatada.loot.grpc.RollEnemyLootResponse.newBuilder()
                .setDropped(dto.isDropped());

        if (dto.isDropped() && dto.getItemId() != null)
            responseBuilder.setItemId(dto.getItemId().intValue());

        return responseBuilder.build();
    }
}
