package com.chocolatada.playerclass.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    private PlayerClassEntity playerClass;

    @Column(nullable = false)
    private float critRateModifier;

    @Column(nullable = false)
    private float critDamageModifier;

    @Column(nullable = false)
    private float hpModifier;

    @Column(nullable = false)
    private float atkModifier;

    @Column(nullable = false)
    private float defModifier;

    @Column(nullable = false)
    private float staminaModifier;

    @Column(nullable = false)
    private float accuracyModifier;

    @Column(nullable = false)
    private float evasionModifier;
}
