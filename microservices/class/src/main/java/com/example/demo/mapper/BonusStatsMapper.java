package com.example.demo.mapper;

import com.example.demo.grpc.BonusStats;

public class BonusStatsMapper {
    public static BonusStats toBonusStatsGrpc(com.example.demo.dto.BonusStatsDTO bonusStatsDto) {
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

    public static com.example.demo.dto.BonusStatsDTO toBonusStatsDto(BonusStats bonusStatsGrpc) {
        com.example.demo.dto.BonusStatsDTO bonusStatsDto = new com.example.demo.dto.BonusStatsDTO();
        bonusStatsDto.setHp(bonusStatsGrpc.getHp());
        bonusStatsDto.setAtk(bonusStatsGrpc.getAtk());
        bonusStatsDto.setDef(bonusStatsGrpc.getDef());
        bonusStatsDto.setStamina(bonusStatsGrpc.getStamina());
        bonusStatsDto.setAccuracy(bonusStatsGrpc.getAccuracy());
        bonusStatsDto.setEvasion(bonusStatsGrpc.getEvasion());

        return bonusStatsDto;
    }
}
