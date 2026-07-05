package com.chocolatada.inventory.service.jpa;

import com.chocolatada.inventory.dto.InventoryItemDTO;
import com.chocolatada.inventory.entity.ItemEntity;
import com.chocolatada.inventory.entity.ItemSlot;
import com.chocolatada.inventory.exception.InvalidItemDataException;

import java.util.List;

public interface IItemService {
    ItemEntity findById(Long itemId);

    boolean existsById(Long itemId);

    List<InventoryItemDTO> findByRequiredLevel(Integer requiredLevel, boolean onlyHigherThan);

    List<InventoryItemDTO> findByRequiredLevelAndSlot(Integer requiredLevel, boolean onlyHigherThan, ItemSlot slot);

    void loadItemsFromResources() throws InvalidItemDataException;
}
