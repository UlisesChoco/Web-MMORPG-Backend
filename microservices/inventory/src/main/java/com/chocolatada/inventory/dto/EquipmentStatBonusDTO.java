package com.chocolatada.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentStatBonusDTO {
    private Integer hp;
    private Integer atk;
    private Integer def;
    private Integer stamina;
    private Integer accuracy;
    private Integer evasion;
    private Float critRate;
    private Float critDamage;
}

