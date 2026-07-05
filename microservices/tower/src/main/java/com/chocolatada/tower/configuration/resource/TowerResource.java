package com.chocolatada.tower.configuration.resource;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TowerResource {
    private Integer floor;

    private String levelRange;
}
