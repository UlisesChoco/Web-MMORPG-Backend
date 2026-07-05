package com.chocolatada.tower.mapper;

import com.chocolatada.tower.dto.TowerDTO;
import com.chocolatada.tower.entity.TowerPlayerProgressEntity;

public class TowerPlayerProgressMapper {
    public static TowerDTO toTowerDTO(TowerPlayerProgressEntity entity) {
        return TowerDTO.builder()
                .floor(entity.getTower().getFloor())
                .levelRange(entity.getTower().getLevelRange())
                .build();
    }

    public static com.chocolatada.tower_player_progress.grpc.TowerPlayerProgress toTowerRPC(TowerDTO dto) {
        return com.chocolatada.tower_player_progress.grpc.TowerPlayerProgress.newBuilder()
                .setFloor(dto.getFloor())
                .setLevelRange(dto.getLevelRange())
                .build();
    }
}
