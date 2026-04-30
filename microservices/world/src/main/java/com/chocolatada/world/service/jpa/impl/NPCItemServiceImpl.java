package com.chocolatada.world.service.jpa.impl;

import com.chocolatada.world.dto.NPCItemDTO;
import com.chocolatada.world.entity.NPCItemEntity;
import com.chocolatada.world.exception.NPCItemNotFoundException;
import com.chocolatada.world.mapper.NPCMapper;
import com.chocolatada.world.repository.INPCItemRepository;
import com.chocolatada.world.service.jpa.INPCItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NPCItemServiceImpl implements INPCItemService {
    private final INPCItemRepository npcItemRepository;

    @Override
    @Transactional(readOnly = true)
    public List<NPCItemDTO> findByNpcId(Long npcId) throws NPCItemNotFoundException {
        List<NPCItemEntity> entities = npcItemRepository.findByNpcId(npcId);
        List<NPCItemDTO> dtos = NPCMapper.toItemDTOs(entities);

        if(dtos.isEmpty())
            throw new NPCItemNotFoundException("No existen items para el NPC de ID: " + npcId);

        return dtos;
    }
}
