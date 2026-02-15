import { SlotType } from "../../util/item.interface";

export interface GetItemsByRequiredLevelAndSlotRequest {
    requiredLevel: Long;
    onlyHigherThan: boolean;
    slot: SlotType;
}