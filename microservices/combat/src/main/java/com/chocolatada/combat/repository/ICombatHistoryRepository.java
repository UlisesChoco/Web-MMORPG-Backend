package com.chocolatada.combat.repository;

import com.chocolatada.combat.entity.CombatHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface ICombatHistoryRepository extends JpaRepository<CombatHistoryEntity, Long> {
    List<CombatHistoryEntity> findByPlayerId(Long playerId);

    @Query("SELECT c FROM CombatHistoryEntity c ORDER BY c.date DESC LIMIT :limit")
    List<CombatHistoryEntity> findRecentCombats(@Param("limit") int limit);

    Long countByPlayerIdAndWasFatalFalse(Long playerId);
}
