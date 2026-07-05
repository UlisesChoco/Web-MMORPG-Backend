package com.chocolatada.player.repository;

import com.chocolatada.player.entity.PlayerEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPlayerRepository extends JpaRepository<PlayerEntity, Long> {
    @Query("""
        SELECT p
        FROM PlayerEntity p
        WHERE (:alive IS NULL OR p.alive = :alive)
        ORDER BY p.level DESC
    """)
    List<PlayerEntity> findTopByLevel(@Param("alive") Boolean alive, Pageable pageable);

    Optional<PlayerEntity> findByUserId(Long userId);

    @Modifying
    @Query("""
        UPDATE PlayerEntity p
        SET p.alive = FALSE
        WHERE p.id = :id
    """)
    void markPlayerAsDead(@Param("id") Long id);
}
