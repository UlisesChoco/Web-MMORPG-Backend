package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.PlayerClassModifierEntity;

@Repository
public interface PlayerClassModifierRepository extends JpaRepository<PlayerClassModifierEntity, Long> {
    Optional<PlayerClassModifierEntity> findByPlayerClassId(Long playerClassId);
}
