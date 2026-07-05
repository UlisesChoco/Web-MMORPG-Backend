package com.chocolatada.inventory.repository;

import com.chocolatada.inventory.entity.ItemEntity;
import com.chocolatada.inventory.entity.ItemSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IItemRepository extends JpaRepository<ItemEntity, Long> {
    List<ItemEntity> findByRequiredLevelGreaterThanEqual(Integer requiredLevel);

    List<ItemEntity> findByRequiredLevelLessThanEqual(Integer requiredLevel);

    List<ItemEntity> findByRequiredLevelGreaterThanEqualAndSlot(Integer requiredLevel, ItemSlot slot);

    List<ItemEntity> findByRequiredLevelLessThanEqualAndSlot(Integer requiredLevel, ItemSlot slot);
}
