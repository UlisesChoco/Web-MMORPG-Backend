package com.chocolatada.world.service.jpa.impl;

import com.chocolatada.world.dto.NPCDTO;
import com.chocolatada.world.entity.NPCEntity;
import com.chocolatada.world.exception.NPCNotFoundException;
import com.chocolatada.world.mapper.NPCMapper;
import com.chocolatada.world.repository.INPCRepository;
import com.chocolatada.world.service.jpa.INPCService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NPCServiceImpl implements INPCService {

    private final INPCRepository npcRepository;

    @Override
    @Transactional(readOnly = true)
    public NPCDTO findById(Long npcId) throws NPCNotFoundException {
        NPCEntity entity = npcRepository.findByIdWithItems(npcId)
                .orElseThrow(() -> new NPCNotFoundException(
                        "No se encontró el NPC con ID: " + npcId
                ));

        return NPCMapper.toDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NPCDTO> findAll() {
        List<NPCEntity> entities = npcRepository.findAllWithItems();

        return NPCMapper.toDTOs(entities);
    }
}
