package com.chocolatada.combat.mapper;

import com.chocolatada.combat.domain.Entity;
import com.chocolatada.combat.domain.State;

public class StateMapper {
    public static State toState(Entity entity) {
        return State.builder()
                .hp(entity.getHp())
                .stamina(entity.getStamina())
                .accuracy(entity.getAccuracy())
                .evasion(entity.getEvasion())
                .build();
    }
}
