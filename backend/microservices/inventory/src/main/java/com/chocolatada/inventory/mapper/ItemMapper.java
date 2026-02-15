package com.chocolatada.inventory.mapper;

import com.chocolatada.inventory.dto.InventoryItemDTO;
import com.chocolatada.inventory.entity.ItemEntity;
import com.chocolatada.inventory.entity.ItemSlot;
import com.chocolatada.inventory.entity.ItemType;
import com.chocolatada.inventory.grpc.SlotType;

import java.util.ArrayList;
import java.util.List;

import static com.chocolatada.inventory.grpc.SlotType.SLOT_TYPE_UNSPECIFIED;

public class ItemMapper {
    public static InventoryItemDTO toInventoryItemDTO(ItemEntity entity) {
        return InventoryItemDTO.builder()
                .inventoryItemId(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .gold(entity.getGold())
                .requiredLevel(entity.getRequiredLevel())
                .type(entity.getType())
                .slot(entity.getSlot())
                .hpBonus(entity.getHpBonus())
                .atkBonus(entity.getAtkBonus())
                .defBonus(entity.getDefBonus())
                .staminaBonus(entity.getStaminaBonus())
                .accuracyBonus(entity.getAccuracyBonus())
                .evasionBonus(entity.getEvasionBonus())
                .critRateBonus(entity.getCritRateBonus())
                .critDamageBonus(entity.getCritDamageBonus())
                .equipped(false)
                .build();
    }

    public static List<InventoryItemDTO> toInventoryItemDTOs(List<ItemEntity> entities) {
        List<InventoryItemDTO> dtos = new ArrayList<>();

        for (ItemEntity entity : entities)
            dtos.add(toInventoryItemDTO(entity));

        return dtos;
    }

    public static com.chocolatada.inventory.grpc.Item toProtoItem(InventoryItemDTO dto) {
        return com.chocolatada.inventory.grpc.Item.newBuilder()
                .setInventoryItemId(dto.getInventoryItemId())
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setGold((dto.getGold() != null) ? dto.getGold() : 0)
                .setRequiredLevel((dto.getRequiredLevel() != null) ? dto.getRequiredLevel() : 0)
                .setType(toProtoItemType(dto.getType()))
                .setSlot(toProtoSlotType(dto.getSlot()))
                .setHpBonus((dto.getHpBonus() != null) ? dto.getHpBonus() : 0)
                .setAtkBonus((dto.getAtkBonus() != null) ? dto.getAtkBonus() : 0)
                .setDefBonus((dto.getDefBonus() != null) ? dto.getDefBonus() : 0)
                .setStaminaBonus((dto.getStaminaBonus() != null) ? dto.getStaminaBonus() : 0)
                .setAccuracyBonus((dto.getAccuracyBonus() != null) ? dto.getAccuracyBonus() : 0)
                .setEvasionBonus((dto.getEvasionBonus() != null) ? dto.getEvasionBonus() : 0)
                .setCritRateBonus((dto.getCritRateBonus() != null) ? dto.getCritRateBonus() : 0)
                .setCritDamageBonus((dto.getCritDamageBonus() != null) ? dto.getCritDamageBonus() : 0)
                .setEquipped(dto.getEquipped())
                .build();
    }

    public static List<com.chocolatada.inventory.grpc.Item> toProtoItems(List<InventoryItemDTO> dtos) {
        List<com.chocolatada.inventory.grpc.Item> protoItems = new ArrayList<>();

        for (InventoryItemDTO dto : dtos)
            protoItems.add(toProtoItem(dto));

        return protoItems;
    }

    public static com.chocolatada.inventory.grpc.ItemType toProtoItemType(ItemType type) {
        if (type == null) {
            return com.chocolatada.inventory.grpc.ItemType.WEAPON;
        }
        return switch (type) {
            case WEAPON -> com.chocolatada.inventory.grpc.ItemType.WEAPON;
            case ARMOR -> com.chocolatada.inventory.grpc.ItemType.ARMOR;
            default -> throw new IllegalStateException("ItemType inesperado: " + type);
        };
    }

    public static com.chocolatada.inventory.grpc.SlotType toProtoSlotType(ItemSlot slot) {
        if (slot == null) {
            return SLOT_TYPE_UNSPECIFIED;
        }
        return switch (slot) {
            case HEAD -> com.chocolatada.inventory.grpc.SlotType.HEAD;
            case CHEST -> com.chocolatada.inventory.grpc.SlotType.CHEST;
            case LEGS -> com.chocolatada.inventory.grpc.SlotType.LEGS;
            case FEET -> com.chocolatada.inventory.grpc.SlotType.FEET;
            case HANDS -> com.chocolatada.inventory.grpc.SlotType.HANDS;
            case MAIN_HAND -> com.chocolatada.inventory.grpc.SlotType.MAIN_HAND;
            case OFF_HAND -> com.chocolatada.inventory.grpc.SlotType.OFF_HAND;
            case RING -> com.chocolatada.inventory.grpc.SlotType.RING;
            case NECKLACE -> com.chocolatada.inventory.grpc.SlotType.NECKLACE;
            default -> throw new IllegalStateException("ItemSlot inesperado: " + slot);
        };
    }

    public static ItemSlot toJavaItemSlot(com.chocolatada.inventory.grpc.SlotType protoSlot) {
        if (protoSlot == null) {
            return ItemSlot.NONE;
        }
        return switch (protoSlot) {
            case HEAD -> ItemSlot.HEAD;
            case CHEST -> ItemSlot.CHEST;
            case LEGS -> ItemSlot.LEGS;
            case FEET -> ItemSlot.FEET;
            case HANDS -> ItemSlot.HANDS;
            case MAIN_HAND -> ItemSlot.MAIN_HAND;
            case OFF_HAND -> ItemSlot.OFF_HAND;
            case RING -> ItemSlot.RING;
            case NECKLACE -> ItemSlot.NECKLACE;
            case SLOT_TYPE_UNSPECIFIED -> ItemSlot.NONE;
            case UNRECOGNIZED -> ItemSlot.NONE;
        };
    }

    public static ItemEntity toItemEntity(com.chocolatada.inventory.configuration.resources.ItemResource resource) {
        ItemEntity entity = new ItemEntity();
        entity.setName(resource.getName());
        entity.setDescription(resource.getDescription());
        entity.setGold(resource.getGold());
        entity.setRequiredLevel(resource.getRequiredLevel());
        entity.setType(resource.getType());
        entity.setSlot(resource.getSlot());
        entity.setHpBonus(resource.getHpBonus());
        entity.setAtkBonus(resource.getAtkBonus());
        entity.setDefBonus(resource.getDefBonus());
        entity.setStaminaBonus(resource.getStaminaBonus());
        entity.setAccuracyBonus(resource.getAccuracyBonus());
        entity.setEvasionBonus(resource.getEvasionBonus());
        entity.setCritRateBonus(resource.getCritRateBonus());
        entity.setCritDamageBonus(resource.getCritDamageBonus());
        return entity;
    }

    public static List<ItemEntity> toItemEntities(List<com.chocolatada.inventory.configuration.resources.ItemResource> resources) {
        List<ItemEntity> entities = new ArrayList<>();

        for (com.chocolatada.inventory.configuration.resources.ItemResource resource : resources)
            entities.add(toItemEntity(resource));

        return entities;
    }
}
