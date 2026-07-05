package com.chocolatada.world.mapper;

import com.chocolatada.world.dto.EnemyDTO;
import com.chocolatada.world.dto.MapDTO;
import com.chocolatada.world.dto.MapEnemyDTO;
import com.chocolatada.world.entity.EnemyEntity;
import com.chocolatada.world.entity.EnemyType;
import com.chocolatada.world.entity.MapEntity;

import java.util.ArrayList;
import java.util.List;

public class MapMapper {

    public static MapDTO toDTO(MapEntity entity) {
        return MapDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .rangeLevel(entity.getRangeLevel())
                .build();
    }

    public static List<MapDTO> toDTOs(List<MapEntity> entities) {
        List<MapDTO> dtos = new ArrayList<>();
        for (MapEntity entity : entities)
            dtos.add(toDTO(entity));
        return dtos;
    }

    public static MapEnemyDTO toMapEnemyDTO(EnemyEntity entity) {
        return MapEnemyDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .gold(entity.getGold())
                .build();
    }

    public static List<MapEnemyDTO> toMapEnemyDTOs(List<EnemyEntity> entities) {
        if (entities == null)
            return new ArrayList<>();

        List<MapEnemyDTO> dtos = new ArrayList<>();
        for (EnemyEntity entity : entities)
            dtos.add(toMapEnemyDTO(entity));
        return dtos;
    }

    public static EnemyDTO toEnemyDTO(EnemyEntity entity) {
        return EnemyDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .type(entity.getType())
                .level(entity.getLevel())
                .experience(entity.getExperience())
                .gold(entity.getGold())
                .critRate(entity.getCritRate())
                .critDamage(entity.getCritDamage())
                .hp(entity.getHp())
                .atk(entity.getAtk())
                .def(entity.getDef())
                .stamina(entity.getStamina())
                .accuracy(entity.getAccuracy())
                .evasion(entity.getEvasion())
                .build();
    }

    public static List<EnemyDTO> toEnemyDTOs(List<EnemyEntity> entities) {
        if (entities == null)
            return new ArrayList<>();

        List<EnemyDTO> dtos = new ArrayList<>();
        for (EnemyEntity entity : entities)
            dtos.add(toEnemyDTO(entity));
        return dtos;
    }

    public static com.chocolatada.world.grpc.Map toGrpcMap(MapDTO dto) {
        return com.chocolatada.world.grpc.Map.newBuilder()
                .setId(dto.getId())
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setRangeLevel(dto.getRangeLevel())
                .build();
    }

    public static List<com.chocolatada.world.grpc.Map> toGrpcMaps(List<MapDTO> dtos) {
        List<com.chocolatada.world.grpc.Map> grpcMaps = new ArrayList<>();
        for (MapDTO dto : dtos)
            grpcMaps.add(toGrpcMap(dto));
        return grpcMaps;
    }

    public static com.chocolatada.world.grpc.MapEnemy toGrpcMapEnemy(MapEnemyDTO dto) {
        return com.chocolatada.world.grpc.MapEnemy.newBuilder()
                .setId(dto.getId())
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setGold(dto.getGold())
                .build();
    }

    public static List<com.chocolatada.world.grpc.MapEnemy> toGrpcMapEnemies(List<MapEnemyDTO> dtos) {
        if (dtos == null)
            return new ArrayList<>();

        List<com.chocolatada.world.grpc.MapEnemy> grpcEnemies = new ArrayList<>();
        for (MapEnemyDTO dto : dtos)
            grpcEnemies.add(toGrpcMapEnemy(dto));
        return grpcEnemies;
    }

    public static com.chocolatada.world.grpc.Enemy toGrpcEnemy(EnemyDTO dto) {
        return com.chocolatada.world.grpc.Enemy.newBuilder()
                .setId(dto.getId())
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setType(toGrpcEnemyType(dto.getType()))
                .setLevel(dto.getLevel())
                .setExperience(dto.getExperience())
                .setGold(dto.getGold())
                .setCritRate(dto.getCritRate())
                .setCritDamage(dto.getCritDamage())
                .setHp(dto.getHp())
                .setAtk(dto.getAtk())
                .setDef(dto.getDef())
                .setStamina(dto.getStamina())
                .setAccuracy(dto.getAccuracy())
                .setEvasion(dto.getEvasion())
                .build();
    }

    public static List<com.chocolatada.world.grpc.Enemy> toGrpcEnemies(List<EnemyDTO> dtos) {
        if (dtos == null)
            return new ArrayList<>();

        List<com.chocolatada.world.grpc.Enemy> grpcEnemies = new ArrayList<>();
        for (EnemyDTO dto : dtos)
            grpcEnemies.add(toGrpcEnemy(dto));
        return grpcEnemies;
    }

    public static com.chocolatada.world.grpc.EnemyType toGrpcEnemyType(EnemyType type) {
        if (type == null)
            return com.chocolatada.world.grpc.EnemyType.ENEMY_TYPE_UNSPECIFIED;

        return switch (type) {
            case NORMAL -> com.chocolatada.world.grpc.EnemyType.NORMAL;
            case ELITE -> com.chocolatada.world.grpc.EnemyType.ELITE;
            case BOSS -> com.chocolatada.world.grpc.EnemyType.BOSS;
            case MINIBOSS -> com.chocolatada.world.grpc.EnemyType.MINIBOSS;
            case LEGENDARY -> com.chocolatada.world.grpc.EnemyType.LEGENDARY;
        };
    }
}

