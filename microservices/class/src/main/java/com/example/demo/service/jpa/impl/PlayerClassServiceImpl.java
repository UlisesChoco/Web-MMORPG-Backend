package com.example.demo.service.jpa.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.configuration.resources.definition.player_class.ClassesConfigurationProperties;
import com.example.demo.entity.PlayerClassEntity;
import com.example.demo.exception.InvalidPlayerClassDataException;
import com.example.demo.mapper.PlayerClassMapper;
import com.example.demo.repository.PlayerClassRepository;
import com.example.demo.service.jpa.IPlayerClassService;
import com.example.demo.validator.PlayerClassValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlayerClassServiceImpl implements IPlayerClassService {
    private final PlayerClassRepository playerClassRepository;

    private final ClassesConfigurationProperties classesConfig;

    @Override
    public PlayerClassEntity findById(Long id) throws InvalidPlayerClassDataException {
        return playerClassRepository.findById(id).orElseThrow(() -> 
            new InvalidPlayerClassDataException("Clase de jugador no encontrada con ID: " + id)
        );
    }

    @Override
    public List<PlayerClassEntity> findAll() {
        return playerClassRepository.findAll();
    }

    @Override
    public PlayerClassEntity save(PlayerClassEntity playerClass) throws InvalidPlayerClassDataException {
        PlayerClassValidator.validate(playerClass);

        return playerClassRepository.save(playerClass);
    }

    @Override
    public void saveAllFromResources() throws InvalidPlayerClassDataException {
        PlayerClassValidator.validate(classesConfig.getClasses());

        List<PlayerClassEntity> playerClasses = PlayerClassMapper.toPlayerClasses(classesConfig.getClasses());

        playerClassRepository.saveAll(playerClasses);
    }

    @Override
    public PlayerClassEntity findByName(String name) throws InvalidPlayerClassDataException {
        return playerClassRepository.findByName(name).orElseThrow(() -> 
            new InvalidPlayerClassDataException("Clase de jugador no encontrada con nombre: " + name)
        );
    }
}
