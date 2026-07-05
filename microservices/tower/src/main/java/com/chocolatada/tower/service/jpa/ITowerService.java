package com.chocolatada.tower.service.jpa;

import com.chocolatada.tower.exception.InvalidTowerDataException;
import com.chocolatada.tower.dto.TowerDTO;
import com.chocolatada.tower.entity.TowerEntity;

import java.util.List;

public interface ITowerService {
    List<TowerEntity> findAllOrderedByFloor();

    TowerDTO findById(Long id) throws InvalidTowerDataException;

    TowerEntity findByFloor(Integer floor) throws InvalidTowerDataException;

    void loadFromResources() throws InvalidTowerDataException;
}
