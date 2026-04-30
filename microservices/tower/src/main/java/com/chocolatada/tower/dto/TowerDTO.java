package com.chocolatada.tower.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TowerDTO {
    private Integer floor;

    private String levelRange;
}
