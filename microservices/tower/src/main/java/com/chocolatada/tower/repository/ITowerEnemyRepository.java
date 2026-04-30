package com.chocolatada.tower.repository;

import com.chocolatada.tower.entity.TowerEnemyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITowerEnemyRepository extends JpaRepository<TowerEnemyEntity, Long> {
    List<TowerEnemyEntity> findByTowerId(Long towerId);
}
