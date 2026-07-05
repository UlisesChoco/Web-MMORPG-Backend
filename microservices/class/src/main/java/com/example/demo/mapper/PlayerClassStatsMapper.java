package com.chocolatada.playerclass.mapper;

import com.chocolatada.playerclass.dto.PlayerStatsDTO;
import com.chocolatada.playerclass.grpc.ScaledStats;

public class PlayerClassStatsMapper {
    public static ScaledStats toScaledStatsGrpc(PlayerStatsDTO playerStatsDto) {
        ScaledStats scaledStatsGrpc = ScaledStats.newBuilder()
                .setHp(playerStatsDto.getHp())
                .setAtk(playerStatsDto.getAtk())
                .setDef(playerStatsDto.getDef())
                .setStamina(playerStatsDto.getStamina())
                .setCritRate(playerStatsDto.getCritRate())
                .setCritDamage(playerStatsDto.getCritDamage())
                .setAccuracy(playerStatsDto.getAccuracy())
                .setEvasion(playerStatsDto.getEvasion())
                .build();

        return scaledStatsGrpc;
    }
}
