package com.chocolatada.inventory.service.jpa;

import com.chocolatada.inventory.dto.EquipmentStatBonusDTO;
import com.chocolatada.inventory.dto.InventoryItemDTO;
import com.chocolatada.inventory.entity.ItemEntity;
import com.chocolatada.inventory.exception.InventoryItemNotFoundException;

import java.util.List;

public interface IPlayerInventoryItemService {

    List<InventoryItemDTO> getPlayerInventory(Long playerId);

    List<InventoryItemDTO> getEquippedItems(Long playerId);

    Long addItemToInventory(Long playerId, ItemEntity item);

    void removeItemFromInventory(Long inventoryItemId) throws InventoryItemNotFoundException;

    void equipItem(Long inventoryItemId, Integer playerLevel) throws InventoryItemNotFoundException;

    void unequipItem(Long inventoryItemId) throws InventoryItemNotFoundException;

    EquipmentStatBonusDTO getEquipmentStatBonus(Long playerId);
}
