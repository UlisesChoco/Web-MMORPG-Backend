package com.chocolatada.world.repository;

import com.chocolatada.world.entity.EnemyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEnemyRepository extends JpaRepository<EnemyEntity, Long> {

}
