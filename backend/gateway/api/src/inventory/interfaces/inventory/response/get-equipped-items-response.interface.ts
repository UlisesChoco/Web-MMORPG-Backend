import { Item } from "../../util/item.interface";

export interface GetEquippedItemsResponse {
    message: string;
    items: Array<Item>;
}