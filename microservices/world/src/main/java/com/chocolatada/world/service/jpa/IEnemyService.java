package com.chocolatada.world.service.jpa;

import com.chocolatada.world.dto.EnemyDTO;
import com.chocolatada.world.exception.EnemyNotFoundException;

public interface IEnemyService {
    EnemyDTO findById(Long enemyId) throws EnemyNotFoundException;
}
