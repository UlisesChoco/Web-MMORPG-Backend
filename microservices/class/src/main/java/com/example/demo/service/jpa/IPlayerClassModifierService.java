package com.example.demo.service.jpa;

import com.example.demo.entity.PlayerClassModifierEntity;
import com.example.demo.exception.InvalidPlayerClassDataException;

public interface IPlayerClassModifierService {
    void saveAllFromResources() throws InvalidPlayerClassDataException;
    PlayerClassModifierEntity findById(Long id) throws InvalidPlayerClassDataException;
    PlayerClassModifierEntity findByPlayerClassId(Long playerClassId) throws InvalidPlayerClassDataException;
}
