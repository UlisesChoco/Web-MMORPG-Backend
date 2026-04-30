package com.chocolatada.combat.service.jpa.impl;

import com.chocolatada.combat.dto.CombatHistoryDTO;
import com.chocolatada.combat.entity.CombatHistoryEntity;
import com.chocolatada.combat.exception.CombatHistoryException;
import com.chocolatada.combat.mapper.CombatHistoryMapper;
import com.chocolatada.combat.repository.ICombatHistoryRepository;
import com.chocolatada.combat.service.jpa.ICombatHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CombatHistoryServiceImpl implements ICombatHistoryService {
    private final ICombatHistoryRepository combatHistoryRepository;

    @Override
    public List<CombatHistoryDTO> getCombatHistoryByPlayerId(Long playerId) throws CombatHistoryException {
        if(playerId == null || playerId <= 0)
            throw new CombatHistoryException("El ID del jugador no puede ser nulo o menor o igual a cero.");

        List<CombatHistoryEntity> entities = combatHistoryRepository.findByPlayerId(playerId);

        return CombatHistoryMapper.toCombatHistoryDTOs(entities);
    }

    @Override
    public List<CombatHistoryDTO> getRecentCombats(int limit) throws CombatHistoryException {
        if(limit <= 0)
            throw new CombatHistoryException("El límite debe ser mayor que cero.");

        List<CombatHistoryEntity> entities = combatHistoryRepository.findRecentCombats(limit);

        return CombatHistoryMapper.toCombatHistoryDTOs(entities);
    }

    @Override
    public Long getPlayerWinCount(Long playerId) throws CombatHistoryException {
        if(playerId == null || playerId <= 0)
            throw new CombatHistoryException("El ID del jugador no puede ser nulo o menor o igual a cero.");

        return combatHistoryRepository.countByPlayerIdAndWasFatalFalse(playerId);
    }

    @Override
    public CombatHistoryEntity save(CombatHistoryEntity entity) {
        return combatHistoryRepository.save(entity);
    }
}
