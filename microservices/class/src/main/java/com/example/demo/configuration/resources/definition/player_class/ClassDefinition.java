package com.example.demo.configuration.resources.definition.player_class;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassDefinition {
    private String name;

    private String description;

    private float critRate;

    private float critDamage;

    private int hp;

    private int atk;

    private int def;

    private int stamina;

    private int accuracy;

    private int evasion;
}
