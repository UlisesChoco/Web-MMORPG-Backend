package com.chocolatada.world.repository;

import com.chocolatada.world.entity.NPCEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface INPCRepository extends JpaRepository<NPCEntity, Long> {
    @Query("""
        SELECT n FROM NPCEntity n
        LEFT JOIN FETCH n.items
        WHERE n.id = :id
    """)
    Optional<NPCEntity> findByIdWithItems(@Param("id") Long id);

    @Query("""
        SELECT DISTINCT n FROM NPCEntity n
        LEFT JOIN FETCH n.items
    """)
    java.util.List<NPCEntity> findAllWithItems();
}
