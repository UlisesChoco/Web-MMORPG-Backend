package com.chocolatada.combat.service.jpa;

import com.chocolatada.combat.dto.FatalCombatReplayDTO;
import com.chocolatada.combat.entity.FatalCombatReplayEntity;

import java.util.List;

public interface IFatalCombatReplayService {
    FatalCombatReplayDTO getFatalCombatReplayByCombatHistoryId(Long combatHistoryId);

    String getTurnLogByCombatHistoryId(Long combatHistoryId);

    List<FatalCombatReplayDTO> getRecentFatalities(int limit);

    FatalCombatReplayEntity save(FatalCombatReplayEntity entity);
}
