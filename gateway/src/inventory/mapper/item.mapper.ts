import { Util } from "src/common/util/number.functions";
import { GetItemsByRequiredLevelResponseDTO } from "../dto/item/get-items-by-required-level-response.dto";
import { GetItemsByRequiredLevelResponse } from "../interfaces/item/response/get-items-by-required-level-response.interface";
import { ItemType, SlotType } from "../interfaces/util/item.interface";
import { GetItemsByRequiredLevelAndSlotResponseDTO } from "../dto/item/get-items-by-required-level-and-slot-response.dto";
import { GetItemsByRequiredLevelAndSlotResponse } from "../interfaces/item/response/get-items-by-required-level-and-slot-response.interface";

export class ItemMapper {
    static toGetItemsByRequiredLevelResponseDTO(data: any): GetItemsByRequiredLevelResponseDTO {
        const response = data as GetItemsByRequiredLevelResponse;

        if(!response.items) {
            return {
                message: response.message,
                items: [],
            }
        }

        const items = response.items.map(item => {
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

        const mapped = {
            message: response.message,
            items: items,
        };

        return mapped;
    }

    static toGetItemsByRequiredLevelAndSlotResponseDTO(data: any): GetItemsByRequiredLevelAndSlotResponseDTO {
        const response = data as GetItemsByRequiredLevelAndSlotResponse;

        if(!response.items) {
            return {
                message: response.message,
                items: [],
            }
        }

        const items = response.items.map(item => {
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

        const mapped = {
            message: response.message,
            items: items,
        };

        return mapped;
    }
}