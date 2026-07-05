package com.chocolatada.world.dto;

import com.chocolatada.world.entity.NPCType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NPCDTO {
    private Long id;
    private String name;
    private String description;
    private NPCType type;
    private List<NPCItemDTO> items;
}

