package com.chocolatada.world.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MapDTO {
    private Long id;
    private String name;
    private String description;
    private String rangeLevel;
}

