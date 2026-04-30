package com.chocolatada.loot.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LootDTO {
    private boolean dropped;

    private Long itemId;

    public LootDTO(boolean dropped) {
        this.dropped = dropped;
        this.itemId = null;
    }
}
