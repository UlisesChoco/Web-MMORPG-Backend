package com.chocolatada.world.repository;

import com.chocolatada.world.entity.MapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMapRepository extends JpaRepository<MapEntity, Long> {
    @Query("""
        SELECT m FROM MapEntity m
        LEFT JOIN FETCH m.enemies
        WHERE m.id = :id
    """)
    Optional<MapEntity> findByIdWithEnemies(@Param("id") Long id);

    @Query("""
        SELECT DISTINCT m FROM MapEntity m
    """)
    List<MapEntity> findAllMaps();
}
