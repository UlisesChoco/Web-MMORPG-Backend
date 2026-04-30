package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PlayerStatsDTO {
    private float critRate;

    private float critDamage;

    private int hp;

    private int atk;

    private int def;

    private int stamina;

    private int accuracy;

    private int evasion;
}
