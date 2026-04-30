package com.chocolatada.world.service.jpa;

import com.chocolatada.world.dto.NPCItemDTO;
import com.chocolatada.world.exception.NPCItemNotFoundException;

import java.util.List;

public interface INPCItemService {
    List<NPCItemDTO> findByNpcId(Long npcId) throws NPCItemNotFoundException;
}
