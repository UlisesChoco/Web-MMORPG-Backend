package com.chocolatada.playerclass.service.jpa;

import java.util.List;

import com.chocolatada.playerclass.entity.PlayerClassEntity;
import com.chocolatada.playerclass.exception.InvalidPlayerClassDataException;

public interface IPlayerClassService {
    PlayerClassEntity findById(Long id) throws InvalidPlayerClassDataException;

    PlayerClassEntity findByName(String name) throws InvalidPlayerClassDataException;

    List<PlayerClassEntity> findAll();

    PlayerClassEntity save(PlayerClassEntity playerClass) throws InvalidPlayerClassDataException;

    void saveAllFromResources() throws InvalidPlayerClassDataException;
}
