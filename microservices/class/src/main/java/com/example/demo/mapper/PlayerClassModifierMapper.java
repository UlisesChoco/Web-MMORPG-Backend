package com.example.demo.mapper;

import com.example.demo.configuration.resources.definition.player_class_modifier.ClassModifierDefinition;
import com.example.demo.entity.PlayerClassModifierEntity;
import com.example.demo.grpc.ModifierData;

public class PlayerClassModifierMapper {
    public static PlayerClassModifierEntity toPlayerClassModifier(ClassModifierDefinition definition) {
        PlayerClassModifierEntity classModifier = PlayerClassModifierEntity.builder()
            .critRateModifier(definition.getCritRateModifier())
            .critDamageModifier(definition.getCritDamageModifier())
            .hpModifier(definition.getHpModifier())
            .atkModifier(definition.getAtkModifier())
            .defModifier(definition.getDefModifier())
            .staminaModifier(definition.getStaminaModifier())
            .accuracyModifier(definition.getAccuracyModifier())
            .evasionModifier(definition.getEvasionModifier())
            .build();

        return classModifier;
    }

    public static ModifierData toModifierData(PlayerClassModifierEntity playerClassModifier) {
        ModifierData modifierData = ModifierData.newBuilder()
            .setCritRateModifier(playerClassModifier.getCritRateModifier())
            .setCritDamageModifier(playerClassModifier.getCritDamageModifier())
            .setHpModifier(playerClassModifier.getHpModifier())
            .setAtkModifier(playerClassModifier.getAtkModifier())
            .setDefModifier(playerClassModifier.getDefModifier())
            .setStaminaModifier(playerClassModifier.getStaminaModifier())
            .setAccuracyModifier(playerClassModifier.getAccuracyModifier())
            .setEvasionModifier(playerClassModifier.getEvasionModifier())
            .build();

        return modifierData;
    }
}
