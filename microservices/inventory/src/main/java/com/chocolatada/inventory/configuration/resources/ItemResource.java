package com.chocolatada.inventory.configuration.resources;

import com.chocolatada.inventory.entity.ItemSlot;
import com.chocolatada.inventory.entity.ItemType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemResource {
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
}
