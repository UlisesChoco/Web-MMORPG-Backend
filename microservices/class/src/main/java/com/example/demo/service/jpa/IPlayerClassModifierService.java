package com.chocolatada.playerclass.service.jpa;

import com.chocolatada.playerclass.entity.PlayerClassModifierEntity;
import com.chocolatada.playerclass.exception.InvalidPlayerClassDataException;

public interface IPlayerClassModifierService {
    void saveAllFromResources() throws InvalidPlayerClassDataException;
    PlayerClassModifierEntity findById(Long id) throws InvalidPlayerClassDataException;
    PlayerClassModifierEntity findByPlayerClassId(Long playerClassId) throws InvalidPlayerClassDataException;
}
