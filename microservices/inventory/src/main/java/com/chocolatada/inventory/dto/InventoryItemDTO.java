package com.chocolatada.inventory.dto;

import com.chocolatada.inventory.entity.ItemSlot;
import com.chocolatada.inventory.entity.ItemType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryItemDTO {
    private Long inventoryItemId;

    private String name;

    private String description;

    private Integer gold;

    private Integer requiredLevel;

    private ItemType type;

    private ItemSlot slot;

    private Integer hpBonus;

    private Integer atkBonus;

    private Integer defBonus;

    private Integer staminaBonus;

    private Integer accuracyBonus;

    private Integer evasionBonus;

    private Float critRateBonus;

    private Float critDamageBonus;

    private Boolean equipped;
}
