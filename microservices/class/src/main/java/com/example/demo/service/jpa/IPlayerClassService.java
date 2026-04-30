package com.example.demo.service.jpa;

import java.util.List;

import com.example.demo.entity.PlayerClassEntity;
import com.example.demo.exception.InvalidPlayerClassDataException;

public interface IPlayerClassService {
    PlayerClassEntity findById(Long id) throws InvalidPlayerClassDataException;

    PlayerClassEntity findByName(String name) throws InvalidPlayerClassDataException;

    List<PlayerClassEntity> findAll();

    PlayerClassEntity save(PlayerClassEntity playerClass) throws InvalidPlayerClassDataException;

    void saveAllFromResources() throws InvalidPlayerClassDataException;
}
