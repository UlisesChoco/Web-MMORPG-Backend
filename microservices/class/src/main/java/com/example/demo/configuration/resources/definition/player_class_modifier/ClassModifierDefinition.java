package com.example.demo.configuration.resources.definition.player_class_modifier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassModifierDefinition {
    private String name;

    private float critRateModifier;

    private float critDamageModifier;

    private float hpModifier;

    private float atkModifier;

    private float defModifier;

    private float staminaModifier;

    private float accuracyModifier;

    private float evasionModifier;
}
