package com.chocolatada.tower.service.jpa.impl;

import com.chocolatada.tower.dto.TowerEnemyDTO;
import com.chocolatada.tower.entity.TowerEnemyEntity;
import com.chocolatada.tower.mapper.TowerEnemyMapper;
import com.chocolatada.tower.repository.ITowerEnemyRepository;
import com.chocolatada.tower.service.jpa.ITowerEnemyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TowerEnemyServiceImpl implements ITowerEnemyService {
    private final ITowerEnemyRepository towerEnemyRepository;

    @Override
    public List<TowerEnemyDTO> findByTowerId(Long towerId) {
        List<TowerEnemyEntity> entities = towerEnemyRepository.findByTowerId(towerId);

        return TowerEnemyMapper.toTowerEnemyDTOs(entities);
    }
}
