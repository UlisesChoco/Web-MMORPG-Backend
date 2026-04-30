package com.chocolatada.inventory.mapper;

import com.chocolatada.inventory.dto.EquipmentStatBonusDTO;
import com.chocolatada.inventory.dto.InventoryItemDTO;
import com.chocolatada.inventory.entity.ItemEntity;
import com.chocolatada.inventory.entity.PlayerInventoryItemEntity;

import java.util.ArrayList;
import java.util.List;

public class PlayerInventoryItemMapper {
    public static InventoryItemDTO toInventoryItemDTO(PlayerInventoryItemEntity entity) {
        return InventoryItemDTO.builder()
                .inventoryItemId(entity.getId())
                .name(entity.getItem().getName())
                .description(entity.getItem().getDescription())
                .gold(entity.getItem().getGold())
                .requiredLevel(entity.getItem().getRequiredLevel())
                .type(entity.getItem().getType())
                .slot(entity.getItem().getSlot())
                .atkBonus(entity.getItem().getAtkBonus())
                .defBonus(entity.getItem().getDefBonus())
                .staminaBonus(entity.getItem().getStaminaBonus())
                .accuracyBonus(entity.getItem().getAccuracyBonus())
                .evasionBonus(entity.getItem().getEvasionBonus())
                .critRateBonus(entity.getItem().getCritRateBonus())
                .critDamageBonus(entity.getItem().getCritDamageBonus())
                .equipped(entity.getEquipped())
                .build();
    }

    public static List<InventoryItemDTO> toInventoryItemDTOs(List<PlayerInventoryItemEntity> entities) {
        List<InventoryItemDTO> dtos = new ArrayList<>();

        for (PlayerInventoryItemEntity entity : entities)
            dtos.add(toInventoryItemDTO(entity));

        return dtos;
    }

    public static EquipmentStatBonusDTO toEquipmentStatBonusDTO(List<PlayerInventoryItemEntity> entities) {
        int totalAtk = 0;
        int totalDef = 0;
        int totalStamina = 0;
        int totalAccuracy = 0;
        int totalEvasion = 0;
        float totalCritRate = 0f;
        float totalCritDamage = 0f;

        for (PlayerInventoryItemEntity inventoryItem : entities) {
            ItemEntity item = inventoryItem.getItem();
            totalAtk += item.getAtkBonus();
            totalDef += item.getDefBonus();
            totalStamina += item.getStaminaBonus();
            totalAccuracy += item.getAccuracyBonus();
            totalEvasion += item.getEvasionBonus();
            totalCritRate += item.getCritRateBonus();
            totalCritDamage += item.getCritDamageBonus();
        }

        return EquipmentStatBonusDTO.builder()
                .atk(totalAtk)
                .def(totalDef)
                .stamina(totalStamina)
                .accuracy(totalAccuracy)
                .evasion(totalEvasion)
                .critRate(totalCritRate)
                .critDamage(totalCritDamage)
                .build();
    }
}
