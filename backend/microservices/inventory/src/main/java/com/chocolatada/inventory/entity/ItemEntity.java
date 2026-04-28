package com.chocolatada.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "gold", nullable = false)
    private Integer gold;

    @Column(name = "required_level", nullable = false)
    private Integer requiredLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ItemType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "slot", nullable = false)
    private ItemSlot slot;

    @Column(name = "hp_bonus", nullable = false)
    private Integer hpBonus;

    @Column(name = "atk_bonus", nullable = false)
    private Integer atkBonus;

    @Column(name = "def_bonus", nullable = false)
    private Integer defBonus;

    @Column(name = "stamina_bonus", nullable = false)
    private Integer staminaBonus;

    @Column(name = "accuracy_bonus", nullable = false)
    private Integer accuracyBonus;

    @Column(name = "evasion_bonus", nullable = false)
    private Integer evasionBonus;

    @Column(name = "crit_rate_bonus", nullable = false)
    private Float critRateBonus;

    @Column(name = "crit_damage_bonus", nullable = false)
    private Float critDamageBonus;
}
