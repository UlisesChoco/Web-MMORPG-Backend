package com.chocolatada.world.service.jpa.impl;

import com.chocolatada.world.dto.EnemyDTO;
import com.chocolatada.world.dto.MapDTO;
import com.chocolatada.world.dto.MapEnemyDTO;
import com.chocolatada.world.entity.MapEntity;
import com.chocolatada.world.exception.MapNotFoundException;
import com.chocolatada.world.mapper.MapMapper;
import com.chocolatada.world.repository.IMapRepository;
import com.chocolatada.world.service.jpa.IMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements IMapService {

    private final IMapRepository mapRepository;

    @Override
    @Transactional(readOnly = true)
    public MapDTO findById(Long mapId) throws MapNotFoundException {
        MapEntity entity = mapRepository.findById(mapId)
                .orElseThrow(() -> new MapNotFoundException(
                        "No se encontró el mapa con ID: " + mapId
                ));

        return MapMapper.toDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MapDTO> findAll() {
        List<MapEntity> entities = mapRepository.findAllMaps();

        return MapMapper.toDTOs(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MapEnemyDTO> findMapEnemies(Long mapId) throws MapNotFoundException {
        MapEntity entity = mapRepository.findByIdWithEnemies(mapId)
                .orElseThrow(() -> new MapNotFoundException(
                        "No se encontró el mapa con ID: " + mapId
                ));

        return MapMapper.toMapEnemyDTOs(entity.getEnemies());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnemyDTO> findExpandedMapEnemyPool(Long mapId) throws MapNotFoundException {
        MapEntity entity = mapRepository.findByIdWithEnemies(mapId)
                .orElseThrow(() -> new MapNotFoundException(
                        "No se encontró el mapa con ID: " + mapId
                ));

        return MapMapper.toEnemyDTOs(entity.getEnemies());
    }
}
