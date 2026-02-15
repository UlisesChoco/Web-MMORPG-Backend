import { Item } from "../../util/item.interface";

export class GetPlayerInventoryResponse {
    message: string;
    items: Array<Item>;
}