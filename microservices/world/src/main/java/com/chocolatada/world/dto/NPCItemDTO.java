package com.chocolatada.world.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NPCItemDTO {
    private Long id;
    private Long itemId;
    private Integer price;
}

