import { ItemDTO } from "../util/item.dto";

export interface GetEquippedItemsResponseDTO {
    message: string;
    items: Array<ItemDTO>;
}