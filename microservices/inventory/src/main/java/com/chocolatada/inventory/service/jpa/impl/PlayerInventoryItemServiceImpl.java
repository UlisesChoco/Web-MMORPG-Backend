package com.chocolatada.inventory.service.jpa.impl;

import com.chocolatada.inventory.dto.EquipmentStatBonusDTO;
import com.chocolatada.inventory.dto.InventoryItemDTO;
import com.chocolatada.inventory.entity.ItemEntity;
import com.chocolatada.inventory.entity.ItemSlot;
import com.chocolatada.inventory.entity.PlayerInventoryItemEntity;
import com.chocolatada.inventory.exception.InvalidEquipmentException;
import com.chocolatada.inventory.exception.InventoryItemNotFoundException;
import com.chocolatada.inventory.mapper.PlayerInventoryItemMapper;
import com.chocolatada.inventory.repository.IPlayerInventoryItemRepository;
import com.chocolatada.inventory.service.jpa.IPlayerInventoryItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlayerInventoryItemServiceImpl implements IPlayerInventoryItemService {

    private final IPlayerInventoryItemRepository playerInventoryItemRepository;

    @Override
    public List<InventoryItemDTO> getPlayerInventory(Long playerId) {
        List<PlayerInventoryItemEntity> inventoryItems =
                playerInventoryItemRepository.findByPlayerId(playerId);

        return PlayerInventoryItemMapper.toInventoryItemDTOs(inventoryItems);
    }

    @Override
    public List<InventoryItemDTO> getEquippedItems(Long playerId) {
        List<PlayerInventoryItemEntity> equippedItems =
                playerInventoryItemRepository.findByPlayerIdAndEquippedTrue(playerId);

        return PlayerInventoryItemMapper.toInventoryItemDTOs(equippedItems);
    }

    @Override
    @Transactional
    public Long addItemToInventory(Long playerId, ItemEntity item) {
        PlayerInventoryItemEntity inventoryItem = PlayerInventoryItemEntity.builder()
                .playerId(playerId)
                .item(item)
                .equipped(false)
                .build();

        PlayerInventoryItemEntity savedItem = playerInventoryItemRepository.save(inventoryItem);

        return savedItem.getId();
    }

    @Override
    @Transactional
    public void removeItemFromInventory(Long inventoryItemId) throws InventoryItemNotFoundException {
        PlayerInventoryItemEntity inventoryItem = findInventoryItemById(inventoryItemId);

        playerInventoryItemRepository.delete(inventoryItem);
    }

    @Override
    @Transactional
    public void equipItem(Long inventoryItemId, Integer playerLevel) throws InventoryItemNotFoundException {
        PlayerInventoryItemEntity inventoryItem = findInventoryItemById(inventoryItemId);
        ItemEntity item = inventoryItem.getItem();

        if (playerLevel < item.getRequiredLevel())
            throw new InvalidEquipmentException("El nivel del jugador es insuficiente para equipar este objeto");

        if (item.getSlot() == ItemSlot.NONE)
            throw new InvalidEquipmentException("Este objeto no puede ser equipado");

        Optional<PlayerInventoryItemEntity> currentEquipped =
                playerInventoryItemRepository.findEquippedByPlayerIdAndSlot(
                        inventoryItem.getPlayerId(),
                        item.getSlot()
                );

        if (currentEquipped.isPresent()) {
            PlayerInventoryItemEntity equippedItem = currentEquipped.get();
            equippedItem.setEquipped(false);
            playerInventoryItemRepository.save(equippedItem);
        }

        inventoryItem.setEquipped(true);
        playerInventoryItemRepository.save(inventoryItem);
    }

    @Override
    @Transactional
    public void unequipItem(Long inventoryItemId) throws InventoryItemNotFoundException {
        PlayerInventoryItemEntity inventoryItem = findInventoryItemById(inventoryItemId);

        if (!inventoryItem.getEquipped())
            throw new InvalidEquipmentException("El objeto no está equipado");

        inventoryItem.setEquipped(false);
        playerInventoryItemRepository.save(inventoryItem);
    }

    @Override
    public EquipmentStatBonusDTO getEquipmentStatBonus(Long playerId) {
        List<PlayerInventoryItemEntity> equippedItems =
                playerInventoryItemRepository.findByPlayerIdAndEquippedTrue(playerId);

        return PlayerInventoryItemMapper.toEquipmentStatBonusDTO(equippedItems);
    }

    private PlayerInventoryItemEntity findInventoryItemById(Long inventoryItemId) {
        return playerInventoryItemRepository.findById(inventoryItemId)
                .orElseThrow(() ->
                    new InventoryItemNotFoundException("Objeto de inventario no encontrado")
                );
    }
}
