package com.chocolatada.tower.service.jpa;

import com.chocolatada.tower.dto.TowerEnemyDTO;

import java.util.List;

public interface ITowerEnemyService {
    List<TowerEnemyDTO> findByTowerId(Long towerId);
}
