package com.chocolatada.combat.service.domain;

import com.chocolatada.combat.domain.Combat;
import com.chocolatada.combat.domain.Entity;
import io.grpc.StatusRuntimeException;

public interface ICombatService {
    Combat processCombat(Entity player, Entity enemy) throws StatusRuntimeException;

    void logCombatTurns(Combat combat);
}
