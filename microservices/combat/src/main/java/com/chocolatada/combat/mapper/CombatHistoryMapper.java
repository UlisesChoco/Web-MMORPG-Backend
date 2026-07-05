package com.chocolatada.combat.mapper;

import com.chocolatada.combat.dto.CombatHistoryDTO;
import com.chocolatada.combat.entity.CombatHistoryEntity;
import com.chocolatada.combat.grpc.CombatHistory;

import java.util.ArrayList;
import java.util.List;

public class CombatHistoryMapper {
    public static CombatHistoryDTO toCombatHistoryDTO(CombatHistoryEntity entity) {
        return CombatHistoryDTO.builder()
                .enemyId(entity.getEnemyId())
                .totalTurns(entity.getTotalTurns())
                .wasFatal(entity.getWasFatal())
                .date(entity.getDate())
                .build();
    }

    public static List<CombatHistoryDTO> toCombatHistoryDTOs(List<CombatHistoryEntity> entities) {
        List<CombatHistoryDTO> dtos = new ArrayList<>();

        for(CombatHistoryEntity entity : entities)
            dtos.add(toCombatHistoryDTO(entity));

        return dtos;
    }

    public static CombatHistory toGrpcCombatHistory(CombatHistoryDTO dto) {
        return CombatHistory.newBuilder()
                .setEnemyId(dto.getEnemyId())
                .setWasFatal(dto.getWasFatal())
                .setDate(dto.getDate().toString())
                .setTotalTurns(dto.getTotalTurns())
                .build();
    }

    public static List<CombatHistory> toGrpcCombatHistories(List<CombatHistoryDTO> dtos) {
        List<CombatHistory> grpcHistories = new ArrayList<>();

        for(CombatHistoryDTO dto : dtos)
            grpcHistories.add(toGrpcCombatHistory(dto));

        return grpcHistories;
    }
}
