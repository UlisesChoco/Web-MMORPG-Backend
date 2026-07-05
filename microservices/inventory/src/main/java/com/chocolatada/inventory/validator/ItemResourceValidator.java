package com.chocolatada.inventory.validator;

import com.chocolatada.inventory.entity.ItemEntity;
import com.chocolatada.inventory.entity.ItemSlot;
import com.chocolatada.inventory.entity.ItemType;
import com.chocolatada.inventory.exception.InvalidItemDataException;

public class ItemResourceValidator {
    public static boolean validate(ItemEntity entity) throws InvalidItemDataException {
        if(!isValidName(entity.getName()))
            throw new InvalidItemDataException("El nombre del item no puede estar vacío y debe tener menos de 100 caracteres.");

        if(!isValidDescription(entity.getDescription()))
            throw new InvalidItemDataException("La descripción del item debe tener menos de 500 caracteres.");

        if(!isValidPrice(entity.getGold()))
            throw new InvalidItemDataException("El precio del item no puede ser negativo.");

        if(!isValidRequiredLevel(entity.getRequiredLevel()))
            throw new InvalidItemDataException("El nivel requerido del item no puede ser negativo.");

        if(!isValidType(entity.getType()))
            throw new InvalidItemDataException("El tipo de item no es válido.");

        if(!isValidSlot(entity.getSlot()))
            throw new InvalidItemDataException("La ranura del item no es válida.");

        return true;
    }

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.length() <= 100;
    }

    public static boolean isValidDescription(String description) {
        return description == null || description.length() <= 500;
    }

    public static boolean isValidPrice(double price) {
        return price >= 0;
    }

    public static boolean isValidRequiredLevel(int level) {
        return level >= 0;
    }

    public static boolean isValidType(ItemType type) {
        return type != null && (type == ItemType.CONSUMABLE || type == ItemType.WEAPON || type == ItemType.ARMOR || type == ItemType.MATERIAL || type == ItemType.ACCESSORY);
    }

    public static boolean isValidSlot(ItemSlot slot) {
        return slot != null && (slot == ItemSlot.HEAD || slot == ItemSlot.CHEST || slot == ItemSlot.LEGS || slot == ItemSlot.FEET || slot == ItemSlot.HANDS || slot == ItemSlot.OFF_HAND || slot == ItemSlot.MAIN_HAND || slot == ItemSlot.NECKLACE || slot == ItemSlot.RING || slot == ItemSlot.NONE);
    }
}
