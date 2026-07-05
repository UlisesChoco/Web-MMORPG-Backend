package com.chocolatada.world.service.jpa;

import com.chocolatada.world.dto.NPCDTO;
import com.chocolatada.world.exception.NPCNotFoundException;

import java.util.List;

public interface INPCService {
    NPCDTO findById(Long npcId) throws NPCNotFoundException;

    List<NPCDTO> findAll();
}
