package com.chocolatada.tower.repository;

import com.chocolatada.tower.entity.TowerPlayerProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITowerPlayerProgressRepository extends JpaRepository<TowerPlayerProgressEntity, Long> {
    @Query("""
    SELECT tpp FROM TowerPlayerProgressEntity tpp
    JOIN FETCH tpp.tower
    WHERE tpp.playerId = :playerId
    """)
    Optional<TowerPlayerProgressEntity> findById(Long playerId);
}
