package com.chocolatada.world.dto;

import com.chocolatada.world.entity.EnemyType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnemyDTO {
    private Long id;
    private String name;
    private String description;
    private EnemyType type;
    private Integer level;
    private Integer experience;
    private Integer gold;
    private Float critRate;
    private Float critDamage;
    private Integer hp;
    private Integer atk;
    private Integer def;
    private Integer stamina;
    private Integer accuracy;
    private Integer evasion;
}

