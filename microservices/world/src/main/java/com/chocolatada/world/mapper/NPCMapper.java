package com.chocolatada.world.mapper;

import com.chocolatada.world.dto.NPCItemDTO;
import com.chocolatada.world.dto.NPCDTO;
import com.chocolatada.world.entity.NPCEntity;
import com.chocolatada.world.entity.NPCItemEntity;
import com.chocolatada.world.entity.NPCType;

import java.util.ArrayList;
import java.util.List;

public class NPCMapper {

    public static NPCDTO toDTO(NPCEntity entity) {
        return NPCDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .type(entity.getType())
                .items(toItemDTOs(entity.getItems()))
                .build();
    }

    public static List<NPCDTO> toDTOs(List<NPCEntity> entities) {
        List<NPCDTO> dtos = new ArrayList<>();
        for (NPCEntity entity : entities)
            dtos.add(toDTO(entity));
        return dtos;
    }

    public static NPCItemDTO toItemDTO(NPCItemEntity entity) {
        return NPCItemDTO.builder()
                .id(entity.getId())
                .itemId(entity.getItemId())
                .price(entity.getPrice())
                .build();
    }

    public static List<NPCItemDTO> toItemDTOs(List<NPCItemEntity> entities) {
        if(entities == null)
            return new ArrayList<>();

        List<NPCItemDTO> dtos = new ArrayList<>();
        for (NPCItemEntity entity : entities)
            dtos.add(toItemDTO(entity));
        return dtos;
    }

    // ===== Conversiones a gRPC =====

    public static com.chocolatada.world.grpc.NPC toGrpcNPC(NPCDTO dto) {
        return com.chocolatada.world.grpc.NPC.newBuilder()
                .setId(dto.getId())
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setType(toGrpcNPCType(dto.getType()))
                .build();
    }

    public static List<com.chocolatada.world.grpc.NPC> toGrpcNPCs(List<NPCDTO> dtos) {
        List<com.chocolatada.world.grpc.NPC> grpcNPCs = new ArrayList<>();
        for (NPCDTO dto : dtos)
            grpcNPCs.add(toGrpcNPC(dto));
        return grpcNPCs;
    }

    public static com.chocolatada.world.grpc.NPCType toGrpcNPCType(NPCType type) {
        if (type == null)
            return com.chocolatada.world.grpc.NPCType.NPC_TYPE_UNSPECIFIED;

        return switch (type) {
            case MERCHANT -> com.chocolatada.world.grpc.NPCType.MERCHANT;
            case QUEST_GIVER -> com.chocolatada.world.grpc.NPCType.QUEST_GIVER;
            case TRAINER -> com.chocolatada.world.grpc.NPCType.TRAINER;
            case BANKER -> com.chocolatada.world.grpc.NPCType.BANKER;
            case BLACKSMITH -> com.chocolatada.world.grpc.NPCType.BLACKSMITH;
        };
    }

    public static com.chocolatada.world.grpc.NPCItem toGrpcNPCItem(NPCItemDTO dto) {
        return com.chocolatada.world.grpc.NPCItem.newBuilder()
                .setId(dto.getId())
                .setItemId(dto.getItemId())
                .setPrice(dto.getPrice())
                .build();
    }

    public static List<com.chocolatada.world.grpc.NPCItem> toGrpcNPCItems(List<NPCItemDTO> dtos) {
        if (dtos == null)
            return new ArrayList<>();

        List<com.chocolatada.world.grpc.NPCItem> grpcItems = new ArrayList<>();
        for (NPCItemDTO dto : dtos)
            grpcItems.add(toGrpcNPCItem(dto));
        return grpcItems;
    }
}

