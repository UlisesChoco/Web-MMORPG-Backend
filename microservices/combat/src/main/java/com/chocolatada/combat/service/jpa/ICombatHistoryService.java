package com.chocolatada.combat.service.jpa;

import com.chocolatada.combat.dto.CombatHistoryDTO;
import com.chocolatada.combat.entity.CombatHistoryEntity;
import com.chocolatada.combat.exception.CombatHistoryException;

import java.util.List;

public interface ICombatHistoryService {
    List<CombatHistoryDTO> getCombatHistoryByPlayerId(Long playerId) throws CombatHistoryException;

    List<CombatHistoryDTO> getRecentCombats(int limit) throws CombatHistoryException;

    Long getPlayerWinCount(Long playerId) throws CombatHistoryException;

    CombatHistoryEntity save(CombatHistoryEntity entity);
}
