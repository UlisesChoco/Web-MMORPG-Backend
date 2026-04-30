package com.chocolatada.player.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "player_class")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "class_id", nullable = false)
    private Long classId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "alive", nullable = false)
    private Boolean alive;

    @Column(name = "gold", nullable = false)
    private int gold;

    @Column(name = "level", nullable = false)
    private int level;

    @Column(name = "experience", nullable = false)
    private int experience;

    @Column(name = "experience_limit", nullable = false)
    private int experienceLimit;

    @Column(name = "free_stat_points", nullable = false)
    private int freeStatPoints;

    @Column(name = "hp_bonus", nullable = false)
    private int hpBonus;

    @Column(name = "atk_bonus", nullable = false)
    private int atkBonus;

    @Column(name = "def_bonus", nullable = false)
    private int defBonus;

    @Column(name = "stamina_bonus", nullable = false)
    private int staminaBonus;

    @Column(name = "accuracy_bonus", nullable = false)
    private int accuracyBonus;

    @Column(name = "evasion_bonus", nullable = false)
    private int evasionBonus;
}
