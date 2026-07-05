package com.chocolatada.world.service.jpa;

import com.chocolatada.world.dto.EnemyDTO;
import com.chocolatada.world.dto.MapDTO;
import com.chocolatada.world.dto.MapEnemyDTO;
import com.chocolatada.world.exception.MapNotFoundException;

import java.util.List;

public interface IMapService {
    MapDTO findById(Long mapId) throws MapNotFoundException;

    List<MapDTO> findAll();

    List<MapEnemyDTO> findMapEnemies(Long mapId) throws MapNotFoundException;

    List<EnemyDTO> findExpandedMapEnemyPool(Long mapId) throws MapNotFoundException;
}
