package com.chocolatada.playerclass.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chocolatada.playerclass.entity.PlayerClassModifierEntity;

@Repository
public interface PlayerClassModifierRepository extends JpaRepository<PlayerClassModifierEntity, Long> {
    Optional<PlayerClassModifierEntity> findByPlayerClassId(Long playerClassId);
}
