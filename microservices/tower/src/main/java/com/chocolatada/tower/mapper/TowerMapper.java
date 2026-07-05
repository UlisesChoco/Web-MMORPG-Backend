package com.chocolatada.tower.mapper;

import com.chocolatada.tower.configuration.resource.TowerResource;
import com.chocolatada.tower.dto.TowerDTO;
import com.chocolatada.tower.entity.TowerEntity;

import java.util.ArrayList;
import java.util.List;

public class TowerMapper {
    public static TowerDTO toDTO(TowerEntity entity) {
        return TowerDTO.builder()
                .floor(entity.getFloor())
                .levelRange(entity.getLevelRange())
                .build();
    }

    public static TowerEntity toEntity(TowerResource resource) {
        return TowerEntity.builder()
                .floor(resource.getFloor())
                .levelRange(resource.getLevelRange())
                .build();
    }

    public static List<TowerEntity> toEntities(List<TowerResource> resources) {
        List<TowerEntity> entities = new ArrayList<>();

        for (TowerResource resource : resources)
            entities.add(toEntity(resource));

        return entities;
    }

    public static com.chocolatada.tower.grpc.Tower toTowerGrpc(TowerEntity entity) {
        return com.chocolatada.tower.grpc.Tower.newBuilder()
                .setId(entity.getId())
                .setFloor(entity.getFloor())
                .setLevelRange(entity.getLevelRange())
                .build();
    }

    public static List<com.chocolatada.tower.grpc.Tower> toTowersGrpc(List<TowerEntity> floors) {
        List<com.chocolatada.tower.grpc.Tower> floorsGrpc = new ArrayList<>();

        for(TowerEntity floor : floors)
            floorsGrpc.add(toTowerGrpc(floor));

        return floorsGrpc;
    }

    public static TowerEntity toEntity(TowerDTO towerDTO) {
        return TowerEntity.builder()
                .floor(towerDTO.getFloor())
                .levelRange(towerDTO.getLevelRange())
                .build();
    }
}
