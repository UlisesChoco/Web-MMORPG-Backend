package com.chocolatada.player.service.jpa;

import com.chocolatada.player.dto.PlayerTopLevelDTO;
import com.chocolatada.player.dto.PlayerUpdateDTO;
import com.chocolatada.player.entity.PlayerEntity;
import com.chocolatada.player.exception.InvalidPlayerDataException;

import java.util.List;

public interface IPlayerService {
    PlayerEntity findById(Long id) throws InvalidPlayerDataException;

    PlayerEntity findByUserId(Long userId) throws InvalidPlayerDataException;

    List<PlayerTopLevelDTO> findTopByLevel(Boolean isAlive, int limit);

    PlayerEntity update(Long id, PlayerUpdateDTO playerUpdateDTO) throws InvalidPlayerDataException;

    boolean deleteById(Long id) throws InvalidPlayerDataException;

    boolean markPlayerAsDead(Long id) throws InvalidPlayerDataException;
}
