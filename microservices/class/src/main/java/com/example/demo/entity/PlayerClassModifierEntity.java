package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "player_class_modifier")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerClassModifierEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_class_id")
    private PlayerClassEntity playerClass;

    @Column(name = "crit_rate_modifier", nullable = false)
    private float critRateModifier;

    @Column(name = "crit_damage_modifier", nullable = false)
    private float critDamageModifier;

    @Column(name = "hp_modifier", nullable = false)
    private float hpModifier;

    @Column(name = "atk_modifier", nullable = false)
    private float atkModifier;

    @Column(name = "def_modifier", nullable = false)
    private float defModifier;

    @Column(name = "stamina_modifier", nullable = false)
    private float staminaModifier;

    @Column(name = "accuracy_modifier", nullable = false)
    private float accuracyModifier;

    @Column(name = "evasion_modifier", nullable = false)
    private float evasionModifier;
}
