package com.chocolatada.combat.service.jpa.impl;

import com.chocolatada.combat.dto.FatalCombatReplayDTO;
import com.chocolatada.combat.entity.FatalCombatReplayEntity;
import com.chocolatada.combat.exception.FatalCombatReplayException;
import com.chocolatada.combat.mapper.FatalCombatReplayMapper;
import com.chocolatada.combat.repository.IFatalCombatReplayRepository;
import com.chocolatada.combat.service.jpa.IFatalCombatReplayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FatalCombatReplayServiceImpl implements IFatalCombatReplayService {
    private final IFatalCombatReplayRepository fatalCombatReplayRepository;

    @Override
    public FatalCombatReplayDTO getFatalCombatReplayByCombatHistoryId(Long combatHistoryId) throws FatalCombatReplayException {
        FatalCombatReplayEntity entity = fatalCombatReplayRepository
                .findByCombatHistoryId(combatHistoryId)
                .orElseThrow(() ->
                        new FatalCombatReplayException("No existe replay fatal para el ID de historial de combate: " + combatHistoryId)
                );

        return FatalCombatReplayMapper.toFatalCombatReplayDTO(entity);
    }

    @Override
    public String getTurnLogByCombatHistoryId(Long combatHistoryId) throws FatalCombatReplayException {
        FatalCombatReplayEntity entity = fatalCombatReplayRepository
                .findByCombatHistoryId(combatHistoryId)
                .orElseThrow(() ->
                        new FatalCombatReplayException("No existe replay fatal para el ID de historial de combate: " + combatHistoryId)
                );

        return entity.getTurnLog();
    }

    @Override
    public List<FatalCombatReplayDTO> getRecentFatalities(int limit) {
        List<FatalCombatReplayEntity> entities = fatalCombatReplayRepository.getRecentFatalities(limit);

        return FatalCombatReplayMapper.toFatalCombatReplayDTOList(entities);
    }

    @Override
    public FatalCombatReplayEntity save(FatalCombatReplayEntity entity) {
        return fatalCombatReplayRepository.save(entity);
    }
}
