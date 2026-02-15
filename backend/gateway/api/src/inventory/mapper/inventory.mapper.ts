import { Util } from "src/common/util/number.functions";
import { GetPlayerInventoryResponse } from "../interfaces/inventory/response/get-player-inventory-response.interface";
import { ItemType, SlotType } from "../interfaces/util/item.interface";
import { GetEquippedItemsResponseDTO } from "../dto/inventory/get-equipped-items-response.dto";
import { GetEquippedItemsResponse } from "../interfaces/inventory/response/get-equipped-items-response.interface";
import { GetPlayerInventoryResponseDTO } from "../dto/inventory/get-player-inventory-response.dto";
import { AddItemToInventoryResponse } from "../interfaces/inventory/response/add-item-to-inventory-response.interface";
import { AddItemToInventoryResponseDTO } from "../dto/inventory/add-item-to-inventory-response.dto";
import { RemoveItemFromInventoryResponse } from "../interfaces/inventory/response/remove-item-from-inventory-response.interface";
import { RemoveItemFromInventoryResponseDTO } from "../dto/inventory/remove-item-from-inventory-response.dto";
import { EquipItemResponse } from "../interfaces/inventory/response/equip-item-response.interface";
import { EquipItemResponseDTO } from "../dto/inventory/equip-item-response.dto";
import { UnequipItemResponseDTO } from "../dto/inventory/unequip-item-response.dto";
import { GetEquipmentStatBonusResponseDTO } from "../dto/inventory/get-equipment-stat-bonus-response.dto";
import { GetEquipmentStatBonusResponse } from "../interfaces/inventory/response/get-equipment-stat-bonus-response.interface";

export class InventoryMapper {
    static toGetPlayerInventoryResponseDTO(data: any): GetPlayerInventoryResponseDTO {
        const response = data as GetPlayerInventoryResponse;

        if(!response.items) {
            return {
                message: response.message,
                items: [],
            }
        }

        const mapped = response.items.map(item => {
            return {
                inventoryItemId: Util.longToNumber(item.inventoryItemId),
                name: item.name,
                description: item.description,
                gold: Util.longToNumber(item.gold),
                requiredLevel: Util.longToNumber(item.requiredLevel),
                type: ItemType[item.type],
                slot: SlotType[item.slot],
                hpBonus: Util.longToNumber(item.hpBonus),
                atkBonus: Util.longToNumber(item.atkBonus),
                defBonus: Util.longToNumber(item.defBonus),
                staminaBonus: Util.longToNumber(item.staminaBonus),
                accuracyBonus: Util.longToNumber(item.accuracyBonus),
                evasionBonus: Util.longToNumber(item.evasionBonus),
                critRateBonus: item.critRateBonus,
                critDamageBonus: item.critDamageBonus,
                equipped: item.equipped ?? false,
            }
        });

        const finalData: GetPlayerInventoryResponseDTO = {
            message: response.message,
            items: mapped,
        };

        return finalData;
    }

    static toGetEquippedItemsResponseDTO(data: any): GetEquippedItemsResponseDTO {
        const response = data as GetEquippedItemsResponse;

        if(!response.items) {
            return {
                message: response.message,
                items: [],
            }
        }

        const mapped = response.items.map(item => {
            return {
                inventoryItemId: Util.longToNumber(item.inventoryItemId),
                name: item.name,
                description: item.description,
                gold: Util.longToNumber(item.gold),
                requiredLevel: Util.longToNumber(item.requiredLevel),
                type: ItemType[item.type],
                slot: SlotType[item.slot],
                hpBonus: Util.longToNumber(item.hpBonus),
                atkBonus: Util.longToNumber(item.atkBonus),
                defBonus: Util.longToNumber(item.defBonus),
                staminaBonus: Util.longToNumber(item.staminaBonus),
                accuracyBonus: Util.longToNumber(item.accuracyBonus),
                evasionBonus: Util.longToNumber(item.evasionBonus),
                critRateBonus: item.critRateBonus,
                critDamageBonus: item.critDamageBonus,
                equipped: item.equipped ?? false,
            }
        });

        const finalData: GetEquippedItemsResponseDTO = {
            message: response.message,
            items: mapped,
        };

        return finalData;
    }

    static toAddItemToInventoryResponseDTO(data: any): AddItemToInventoryResponseDTO {
        const response = data as AddItemToInventoryResponse;

        const mapped = {
            message: response.message,
            inventoryItemId: Util.longToNumber(response.inventoryItemId),
        }

        return mapped;
    }

    static toRemoveItemFromInventoryResponseDTO(data: any): RemoveItemFromInventoryResponseDTO {
        const response = data as RemoveItemFromInventoryResponse;

        const mapped = {
            message: response.message,
        }

        return mapped;
    }

    static toEquipItemResponseDTO(data: any): EquipItemResponseDTO {
        const response = data as EquipItemResponse;
        
        const mapped = {
            message: response.message,
        }

        return mapped;
    }

    static toUnequipItemResponseDTO(data: any): UnequipItemResponseDTO {
        const response = data as UnequipItemResponseDTO;
        
        const mapped = {
            message: response.message,
        }

        return mapped;
    }

    static toGetEquipmentStatBonusResponseDTO(data: any): GetEquipmentStatBonusResponseDTO {
        const response = data as GetEquipmentStatBonusResponse;

        const mapped = {
            hp: response.hp,
            atk: response.atk,
            def: response.def,
            stamina: response.stamina,
            accuracy: response.accuracy,
            evasion: response.evasion,
            critRate: response.critRate,
            critDamage: response.critDamage,
        }

        return mapped;
    }
}