import { Item } from "../../util/item.interface";

export interface GetItemsByRequiredLevelResponse {
    message: string;
    items: Array<Item>;
}