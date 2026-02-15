import { ItemDTO } from "../util/item.dto";

export interface GetItemsByRequiredLevelAndSlotResponseDTO {
    message: string;
    items: Array<ItemDTO>;
}