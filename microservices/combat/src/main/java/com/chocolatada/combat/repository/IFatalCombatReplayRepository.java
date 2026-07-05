package com.chocolatada.combat.repository;

import com.chocolatada.combat.entity.FatalCombatReplayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFatalCombatReplayRepository extends JpaRepository<FatalCombatReplayEntity, Long> {
    Optional<FatalCombatReplayEntity> findByCombatHistoryId(Long combatHistoryId);

    @Query(value = "SELECT f.* FROM fatal_combat_replay f " +
            "JOIN combat_history ch ON f.combat_history_id = ch.id " +
            "ORDER BY ch.date DESC LIMIT :limit", nativeQuery = true)
    List<FatalCombatReplayEntity> getRecentFatalities(int limit);
}
