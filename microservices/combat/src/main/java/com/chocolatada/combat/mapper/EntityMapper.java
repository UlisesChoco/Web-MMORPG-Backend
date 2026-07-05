package com.chocolatada.combat.mapper;

import com.chocolatada.combat.domain.Entity;
import com.chocolatada.combat.grpc.GetEquipmentStatBonusResponse;
import com.chocolatada.combat.grpc.ScaledStats;
import com.chocolatada.combat.grpc.Enemy;

public class EntityMapper {
    public static Entity toEntity(ScaledStats playerScaledStats) {
        return Entity.builder()
                .critRate(playerScaledStats.getCritRate())
                .critDamage(playerScaledStats.getCritDamage())
                .hp(playerScaledStats.getHp())
                .atk(playerScaledStats.getAtk())
                .def(playerScaledStats.getDef())
                .stamina(playerScaledStats.getStamina())
                .accuracy(playerScaledStats.getAccuracy())
                .evasion(playerScaledStats.getEvasion())
                .build();
    }

    public static Entity toEntity(Enemy enemy) {
        return Entity.builder()
                .critRate(enemy.getCritRate())
                .critDamage(enemy.getCritDamage())
                .hp(enemy.getHp())
                .atk(enemy.getAtk())
                .def(enemy.getDef())
                .stamina(enemy.getStamina())
                .accuracy(enemy.getAccuracy())
                .evasion(enemy.getEvasion())
                .build();
    }

    public static Entity toEntity(GetEquipmentStatBonusResponse response) {
        return Entity.builder()
                .critRate(response.getCritRate())
                .critDamage(response.getCritDamage())
                .hp(response.getHp())
                .atk(response.getAtk())
                .def(response.getDef())
                .stamina(response.getStamina())
                .accuracy(response.getAccuracy())
                .evasion(response.getEvasion())
                .build();
    }
}
