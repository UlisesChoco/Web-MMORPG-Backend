package com.chocolatada.inventory.repository;

import com.chocolatada.inventory.entity.ItemSlot;
import com.chocolatada.inventory.entity.PlayerInventoryItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPlayerInventoryItemRepository extends JpaRepository<PlayerInventoryItemEntity, Long> {

    List<PlayerInventoryItemEntity> findByPlayerId(Long playerId);

    List<PlayerInventoryItemEntity> findByPlayerIdAndEquippedTrue(Long playerId);

    @Query("SELECT pii FROM PlayerInventoryItemEntity pii " +
           "WHERE pii.playerId = :playerId " +
           "AND pii.equipped = true " +
           "AND pii.item.slot = :slot")
    Optional<PlayerInventoryItemEntity> findEquippedByPlayerIdAndSlot(
            @Param("playerId") Long playerId,
            @Param("slot") ItemSlot slot
    );

    boolean existsByPlayerIdAndItemId(Long playerId, Long itemId);
}
