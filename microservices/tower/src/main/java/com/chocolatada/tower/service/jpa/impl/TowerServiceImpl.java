package com.chocolatada.tower.service.jpa.impl;

import com.chocolatada.tower.exception.InvalidTowerDataException;
import com.chocolatada.tower.configuration.resource.TowerResources;
import com.chocolatada.tower.dto.TowerDTO;
import com.chocolatada.tower.entity.TowerEntity;
import com.chocolatada.tower.service.jpa.ITowerService;
import com.chocolatada.tower.mapper.TowerMapper;
import com.chocolatada.tower.repository.ITowerRepository;
import com.chocolatada.tower.validator.TowerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TowerServiceImpl implements ITowerService {
    private final ITowerRepository towerRepository;

    private final TowerResources towerResources;

    @Override
    public List<TowerEntity> findAllOrderedByFloor() {
        return towerRepository.findAllByOrderByFloorAsc();
    }

    @Override
    public TowerDTO findById(Long id) throws InvalidTowerDataException {
        TowerEntity entity = towerRepository.findById(id).orElseThrow(() ->
                new InvalidTowerDataException("No se encontró piso con ID: " + id)
        );

        return TowerMapper.toDTO(entity);
    }

    @Override
    public TowerEntity findByFloor(Integer floor) throws InvalidTowerDataException {
        if(!TowerValidator.isValidFloor(floor))
            throw new InvalidTowerDataException("Número de piso inválido: " + floor);

        return towerRepository.findByFloor(floor).orElseThrow(() ->
                new InvalidTowerDataException("No se encontró piso con número: " + floor)
        );
    }

    @Override
    @Transactional
    public void loadFromResources() throws InvalidTowerDataException {
        TowerValidator.validate(towerResources);

        List<TowerEntity> entities = TowerMapper.toEntities(towerResources.getFloors());

        towerRepository.saveAll(entities);
    }
}
