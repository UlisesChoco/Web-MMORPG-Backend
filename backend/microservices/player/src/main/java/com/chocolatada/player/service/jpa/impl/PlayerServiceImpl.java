package com.chocolatada.player.service.jpa.impl;

import com.chocolatada.player.dto.PlayerTopLevelDTO;
import com.chocolatada.player.dto.PlayerUpdateDTO;
import com.chocolatada.player.entity.PlayerEntity;
import com.chocolatada.player.exception.InvalidPlayerDataException;
import com.chocolatada.player.mapper.PlayerMapper;
import com.chocolatada.player.repository.IPlayerRepository;
import com.chocolatada.player.service.domain.IPlayerLevelService;
import com.chocolatada.player.service.jpa.IPlayerService;
import com.chocolatada.player.validator.PlayerValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements IPlayerService {
    private final IPlayerRepository playerRepository;

    private final IPlayerLevelService playerLevelService;

    @Override
    public PlayerEntity findById(Long id) throws InvalidPlayerDataException {
        return playerRepository.findById(id).orElseThrow(() ->
                new InvalidPlayerDataException("No existe un personaje con ID: " + id)
        );
    }

    @Override
    public PlayerEntity findByUserId(Long userId) throws InvalidPlayerDataException {
        return playerRepository.findByUserId(userId).orElseThrow(() ->
                new InvalidPlayerDataException("No existe un personaje con UserID: " + userId)
        );
    }

    @Override
    public List<PlayerTopLevelDTO> findTopByLevel(Boolean isAlive, int limit) {
        Pageable pageable = PageRequest.of(0, limit);

        List<PlayerEntity> players = playerRepository.findTopByLevel(isAlive, pageable);

        return PlayerMapper.toPlayerTopLevelDTOs(players);
    }

    @Transactional
    @Override
    public PlayerEntity update(Long id, PlayerUpdateDTO playerUpdateDTO) throws InvalidPlayerDataException {
        PlayerEntity player = findById(id);

        PlayerValidator.validatePlayerUpdateDTO(playerUpdateDTO);

        player = PlayerMapper.toPlayer(player, playerUpdateDTO);

        if(player.getExperience() >= player.getExperienceLimit()) {
            int newLevel = player.getLevel() + 1;
            player.setLevel(newLevel);
            player.setExperience(0);
            player.setExperienceLimit(playerLevelService.calculateExperienceLimit(newLevel));
            player.setFreeStatPoints(player.getFreeStatPoints() + 1);
        }

        return playerRepository.save(player);
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) throws InvalidPlayerDataException {
        if (!playerRepository.existsById(id))
            throw new InvalidPlayerDataException("No existe un personaje con ID: " + id);

        playerRepository.deleteById(id);

        return true;
    }

    @Transactional
    @Override
    public boolean markPlayerAsDead(Long id) throws InvalidPlayerDataException {
        if (!playerRepository.existsById(id))
            throw new InvalidPlayerDataException("No existe un personaje con ID: " + id);

        playerRepository.markPlayerAsDead(id);

        return true;
    }
}
