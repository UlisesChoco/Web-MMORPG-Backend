package com.chocolatada.loot.configuration.resources;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnemyItemDropResource {
    private Long enemyId;

    private Long itemId;

    private float probability;
}
