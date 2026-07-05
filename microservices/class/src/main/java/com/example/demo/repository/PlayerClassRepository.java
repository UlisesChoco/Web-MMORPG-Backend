package com.chocolatada.playerclass.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chocolatada.playerclass.entity.PlayerClassEntity;

@Repository
public interface PlayerClassRepository extends JpaRepository<PlayerClassEntity, Long> {
    Optional<PlayerClassEntity> findByName(String name);
}
