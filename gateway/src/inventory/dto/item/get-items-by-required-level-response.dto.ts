import { ItemDTO } from "../util/item.dto";

export interface GetItemsByRequiredLevelResponseDTO {
    message: string;
    items: Array<ItemDTO>;
}