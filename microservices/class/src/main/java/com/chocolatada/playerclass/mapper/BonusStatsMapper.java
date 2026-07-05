package com.chocolatada.playerclass.mapper;

import com.chocolatada.playerclass.grpc.BonusStats;

public class BonusStatsMapper {
    public static BonusStats toBonusStatsGrpc(com.chocolatada.playerclass.dto.BonusStatsDTO bonusStatsDto) {
        BonusStats bonusStatsGrpc = BonusStats.newBuilder()
                .setHp(bonusStatsDto.getHp())
                .setAtk(bonusStatsDto.getAtk())
                .setDef(bonusStatsDto.getDef())
                .setStamina(bonusStatsDto.getStamina())
                .setAccuracy(bonusStatsDto.getAccuracy())
                .setEvasion(bonusStatsDto.getEvasion())
                .build();

        return bonusStatsGrpc;
    }

    public static com.chocolatada.playerclass.dto.BonusStatsDTO toBonusStatsDto(BonusStats bonusStatsGrpc) {
        com.chocolatada.playerclass.dto.BonusStatsDTO bonusStatsDto = new com.chocolatada.playerclass.dto.BonusStatsDTO();
        bonusStatsDto.setHp(bonusStatsGrpc.getHp());
        bonusStatsDto.setAtk(bonusStatsGrpc.getAtk());
        bonusStatsDto.setDef(bonusStatsGrpc.getDef());
        bonusStatsDto.setStamina(bonusStatsGrpc.getStamina());
        bonusStatsDto.setAccuracy(bonusStatsGrpc.getAccuracy());
        bonusStatsDto.setEvasion(bonusStatsGrpc.getEvasion());

        return bonusStatsDto;
    }
}
