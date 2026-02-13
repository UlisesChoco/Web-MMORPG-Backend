package com.chocolatada.player.mapper;

import com.chocolatada.player.dto.PlayerTopLevelDTO;
import com.chocolatada.player.dto.PlayerUpdateDTO;
import com.chocolatada.player.entity.PlayerEntity;

import com.chocolatada.player.grpc.PlayerResponse;

import java.util.ArrayList;
import java.util.List;

public class PlayerMapper {
    public static PlayerTopLevelDTO toPlayerTopLevelDTO(PlayerEntity player) {
        PlayerTopLevelDTO dto = new PlayerTopLevelDTO();
        dto.setName(player.getName());
        dto.setLevel(player.getLevel());
        return dto;
    }

    public static List<PlayerTopLevelDTO> toPlayerTopLevelDTOs(List<PlayerEntity> players) {
        List<PlayerTopLevelDTO> playerTopLevelDTOS = new ArrayList<>();

        for (PlayerEntity player : players)
            playerTopLevelDTOS.add(toPlayerTopLevelDTO(player));

        return playerTopLevelDTOS;
    }

    public static PlayerEntity toPlayer(PlayerEntity player, PlayerUpdateDTO playerUpdateDTO) {
        playerUpdateDTO.getName().ifPresent(name -> player.setName(name));
        playerUpdateDTO.getGold().ifPresent(gold -> player.setGold(player.getGold() + gold));
        playerUpdateDTO.getLevel().ifPresent(level -> player.setLevel(level));
        playerUpdateDTO.getExperience().ifPresent(experience -> player.setExperience(player.getExperience() + experience));
        playerUpdateDTO.getExperienceLimit().ifPresent(experienceLimit -> player.setExperienceLimit(experienceLimit));
        playerUpdateDTO.getFreeStatPoints().ifPresent(freeStatPoints -> player.setFreeStatPoints(player.getFreeStatPoints() + freeStatPoints));
        playerUpdateDTO.getHpBonus().ifPresent(hpBonus -> player.setHpBonus(player.getHpBonus() + hpBonus));
        playerUpdateDTO.getAtkBonus().ifPresent(atkBonus -> player.setAtkBonus(player.getAtkBonus() + atkBonus));
        playerUpdateDTO.getDefBonus().ifPresent(defBonus -> player.setDefBonus(player.getDefBonus() + defBonus));
        playerUpdateDTO.getStaminaBonus().ifPresent(staminaBonus -> player.setStaminaBonus(player.getStaminaBonus() + staminaBonus));
        playerUpdateDTO.getAccuracyBonus().ifPresent(accuracyBonus -> player.setAccuracyBonus(player.getAccuracyBonus() + accuracyBonus));
        playerUpdateDTO.getEvasionBonus().ifPresent(evasionBonus -> player.setEvasionBonus(player.getEvasionBonus() + evasionBonus));
        return player;
    }

    public static com.chocolatada.player.grpc.Player toPlayerGrpc(PlayerEntity player) {
        return com.chocolatada.player.grpc.Player.newBuilder()
                .setId(player.getId())
                .setUserId(player.getUserId())
                .setClassId(player.getClassId())
                .setName(player.getName())
                .setGold(player.getGold())
                .setLevel(player.getLevel())
                .setExperience(player.getExperience())
                .setExperienceLimit(player.getExperienceLimit())
                .setFreeStatPoints(player.getFreeStatPoints())
                .setHpBonus(player.getHpBonus())
                .setAtkBonus(player.getAtkBonus())
                .setDefBonus(player.getDefBonus())
                .setStaminaBonus(player.getStaminaBonus())
                .setAccuracyBonus(player.getAccuracyBonus())
                .setEvasionBonus(player.getEvasionBonus())
                .setAlive(player.getAlive())
                .build();
    }

    public static PlayerResponse toPlayerResponse(PlayerEntity player) {
        return PlayerResponse.newBuilder()
                .setPlayer(toPlayerGrpc(player))
                .build();
    }

    public static com.chocolatada.player.grpc.PlayerSummary toPlayerSummaryGrpc(PlayerTopLevelDTO dto) {
        return com.chocolatada.player.grpc.PlayerSummary.newBuilder()
                .setName(dto.getName())
                .setLevel(dto.getLevel())
                .build();
    }

    public static com.chocolatada.player.grpc.TopPlayersResponse toTopPlayersResponse(List<PlayerTopLevelDTO> topPlayers) {
        com.chocolatada.player.grpc.TopPlayersResponse.Builder responseBuilder = com.chocolatada.player.grpc.TopPlayersResponse.newBuilder();

        for (PlayerTopLevelDTO dto : topPlayers) {
            responseBuilder.addPlayers(toPlayerSummaryGrpc(dto));
        }

        return responseBuilder.build();
    }

    /*
    siento que podria mejorar este mapeo, pero al componerse PlayerUpdateData de puros atributos opcionales,
    y PlayerUpdateDTO acepta solo optionals para cada atributo, no veo sentido a hacer algo mas complejo o elegante,
    porque al final voy a tener que hacer una verificacion por cada atributo de todas formas
     */
    public static PlayerUpdateDTO toPlayerUpdateDTO(com.chocolatada.player.grpc.PlayerUpdateData updates) {
        PlayerUpdateDTO dto = new PlayerUpdateDTO();
        if (updates.hasName()) {
            dto.setName(java.util.Optional.of(updates.getName()));
        } else {
            dto.setName(java.util.Optional.empty());
        }
        if (updates.hasGold()) {
            dto.setGold(java.util.Optional.of(updates.getGold()));
        } else {
            dto.setGold(java.util.Optional.empty());
        }
        if (updates.hasLevel()) {
            dto.setLevel(java.util.Optional.of(updates.getLevel()));
        } else {
            dto.setLevel(java.util.Optional.empty());
        }
        if (updates.hasExperience()) {
            dto.setExperience(java.util.Optional.of(updates.getExperience()));
        } else {
            dto.setExperience(java.util.Optional.empty());
        }
        if (updates.hasExperienceLimit()) {
            dto.setExperienceLimit(java.util.Optional.of(updates.getExperienceLimit()));
        } else {
            dto.setExperienceLimit(java.util.Optional.empty());
        }
        if (updates.hasFreeStatPoints()) {
            dto.setFreeStatPoints(java.util.Optional.of(updates.getFreeStatPoints()));
        } else {
            dto.setFreeStatPoints(java.util.Optional.empty());
        }
        if (updates.hasHpBonus()) {
            dto.setHpBonus(java.util.Optional.of(updates.getHpBonus()));
        } else {
            dto.setHpBonus(java.util.Optional.empty());
        }
        if (updates.hasAtkBonus()) {
            dto.setAtkBonus(java.util.Optional.of(updates.getAtkBonus()));
        } else {
            dto.setAtkBonus(java.util.Optional.empty());
        }
        if (updates.hasDefBonus()) {
            dto.setDefBonus(java.util.Optional.of(updates.getDefBonus()));
        } else {
            dto.setDefBonus(java.util.Optional.empty());
        }
        if (updates.hasStaminaBonus()) {
            dto.setStaminaBonus(java.util.Optional.of(updates.getStaminaBonus()));
        } else {
            dto.setStaminaBonus(java.util.Optional.empty());
        }
        if (updates.hasAccuracyBonus()) {
            dto.setAccuracyBonus(java.util.Optional.of(updates.getAccuracyBonus()));
        } else {
            dto.setAccuracyBonus(java.util.Optional.empty());
        }
        if (updates.hasEvasionBonus()) {
            dto.setEvasionBonus(java.util.Optional.of(updates.getEvasionBonus()));
        } else {
            dto.setEvasionBonus(java.util.Optional.empty());
        }
        return dto;
    }
}
