package com.example.demo.mapper;

import com.example.demo.dto.PlayerStatsDTO;
import com.example.demo.grpc.ScaledStats;

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
