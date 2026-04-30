import { ItemDTO } from "../util/item.dto";

export interface GetPlayerInventoryResponseDTO {
    message: string;
    items: Array<ItemDTO>;
}