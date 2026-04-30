package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BonusStatsDTO {
    private int hp;

    private int atk;

    private int def;

    private int stamina;

    private int accuracy;

    private int evasion;
}
