package com.chocolatada.loot.repository;

import com.chocolatada.loot.entity.EnemyItemDropEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEnemyItemDropRepository extends JpaRepository<EnemyItemDropEntity, Long> {
    List<EnemyItemDropEntity> findByEnemyId(Long enemyId);
}
