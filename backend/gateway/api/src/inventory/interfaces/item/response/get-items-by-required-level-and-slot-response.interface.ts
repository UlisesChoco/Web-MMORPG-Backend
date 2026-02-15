import { Item } from "../../util/item.interface";

export interface GetItemsByRequiredLevelAndSlotResponse {
    message: string;
    items: Array<Item>;
}