package com.chocolatada.combat.service.jpa;

import com.chocolatada.combat.dto.FatalCombatReplayDTO;
import com.chocolatada.combat.entity.FatalCombatReplayEntity;
import com.chocolatada.combat.exception.FatalCombatReplayException;

import java.util.List;

public interface IFatalCombatReplayService {
    FatalCombatReplayDTO getFatalCombatReplayByCombatHistoryId(Long combatHistoryId) throws FatalCombatReplayException;

    String getTurnLogByCombatHistoryId(Long combatHistoryId) throws FatalCombatReplayException;

    List<FatalCombatReplayDTO> getRecentFatalities(int limit) throws FatalCombatReplayException;

    FatalCombatReplayEntity save(FatalCombatReplayEntity entity);
}
