package com.chocolatada.world.service.jpa.impl;

import com.chocolatada.world.dto.EnemyDTO;
import com.chocolatada.world.entity.EnemyEntity;
import com.chocolatada.world.exception.EnemyNotFoundException;
import com.chocolatada.world.mapper.MapMapper;
import com.chocolatada.world.repository.IEnemyRepository;
import com.chocolatada.world.service.jpa.IEnemyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EnemyServiceImpl implements IEnemyService {

    private final IEnemyRepository enemyRepository;

    @Override
    @Transactional(readOnly = true)
    public EnemyDTO findById(Long enemyId) throws EnemyNotFoundException {
        EnemyEntity entity = enemyRepository.findById(enemyId)
                .orElseThrow(() -> new EnemyNotFoundException(
                        "No se encontró el enemigo con ID: " + enemyId
                ));

        return MapMapper.toEnemyDTO(entity);
    }
}
