package com.chocolatada.world.repository;

import com.chocolatada.world.entity.NPCItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INPCItemRepository extends JpaRepository<NPCItemEntity, Long> {
    List<NPCItemEntity> findByNpcId(Long npcId);
}
