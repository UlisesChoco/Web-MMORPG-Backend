package com.chocolatada.loot.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnemyItemDropDTO {
    private Long itemId;

    private Float probability;
}
