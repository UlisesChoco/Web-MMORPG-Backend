package com.chocolatada.world.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MapEnemyDTO {
    private Long id;
    private String name;
    private String description;
    private Integer gold;
}

