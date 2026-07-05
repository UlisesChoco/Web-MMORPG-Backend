package com.chocolatada.tower.service.jpa.impl;

import com.chocolatada.tower.dto.TowerDTO;
import com.chocolatada.tower.entity.TowerEntity;
import com.chocolatada.tower.entity.TowerPlayerProgressEntity;
import com.chocolatada.tower.exception.InvalidTowerDataException;
import com.chocolatada.tower.mapper.TowerPlayerProgressMapper;
import com.chocolatada.tower.repository.ITowerPlayerProgressRepository;
import com.chocolatada.tower.service.jpa.ITowerPlayerProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TowerPlayerProgressImpl implements ITowerPlayerProgressService {
    private final ITowerPlayerProgressRepository towerPlayerProgressRepository;

    @Override
    public TowerDTO getMaxFloorReached(Long playerId) throws InvalidTowerDataException {
        TowerPlayerProgressEntity entity = towerPlayerProgressRepository
                .findById(playerId).orElseThrow(() ->
                        new InvalidTowerDataException("No existe progreso para el jugador con ID: " + playerId)
                );

        return TowerPlayerProgressMapper.toTowerDTO(entity);
    }

    @Override
    @Transactional
    public boolean registerPlayerProgress(TowerEntity towerEntity, Long playerId) throws InvalidTowerDataException {
        if(playerId < 1)
            throw new InvalidTowerDataException("El ID del jugador no es válido: " + playerId);

        TowerPlayerProgressEntity entity;

        try {
            entity = getPlayerProgressEntity(playerId);

            entity.setTower(towerEntity);
            entity.setPlayerId(playerId);
        } catch (InvalidTowerDataException invalidTowerDataException) {
            //if doesn't exist create new one
            entity = TowerPlayerProgressEntity.builder()
                    .tower(towerEntity)
                    .playerId(playerId)
                    .build();
        }

        towerPlayerProgressRepository.save(entity);

        return true;
    }

    @Override
    public TowerPlayerProgressEntity getPlayerProgressEntity(Long playerId) throws InvalidTowerDataException {
        return towerPlayerProgressRepository.findById(playerId).orElseThrow(() ->
                new InvalidTowerDataException("No existe progreso para el jugador con ID: " + playerId)
        );
    }
}
