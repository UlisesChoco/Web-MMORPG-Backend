import { Observable } from "rxjs";
import { AddItemToInventoryRequest } from "../interfaces/inventory/request/add-item-to-inventory-request.interface";
import { EquipItemRequest } from "../interfaces/inventory/request/equip-item-request.interface";
import { GetEquipmentStatBonusRequest } from "../interfaces/inventory/request/get-equipment-stat-bonus-request.interface";
import { GetEquippedItemsRequest } from "../interfaces/inventory/request/get-equipped-items-request.interface";
import { GetPlayerInventoryRequest } from "../interfaces/inventory/request/get-player-inventory-request.interface";
import { RemoveItemFromInventoryRequest } from "../interfaces/inventory/request/remove-item-from-inventory-request.interface";
import { UnequipItemRequest } from "../interfaces/inventory/request/unequip-item-request.interface";
import { AddItemToInventoryResponse } from "../interfaces/inventory/response/add-item-to-inventory-response.interface";
import { EquipItemResponse } from "../interfaces/inventory/response/equip-item-response.interface";
import { GetEquipmentStatBonusResponse } from "../interfaces/inventory/response/get-equipment-stat-bonus-response.interface";
import { GetEquippedItemsResponse } from "../interfaces/inventory/response/get-equipped-items-response.interface";
import { GetPlayerInventoryResponse } from "../interfaces/inventory/response/get-player-inventory-response.interface";
import { RemoveItemFromInventoryResponse } from "../interfaces/inventory/response/remove-item-from-inventory-response.interface";
import { UnequipItemResponse } from "../interfaces/inventory/response/unequip-item-response.interface";

export interface InventoryService {
    GetPlayerInventory(request: GetPlayerInventoryRequest): Observable<GetPlayerInventoryResponse>;
    GetEquippedItems(request: GetEquippedItemsRequest): Observable<GetEquippedItemsResponse>;
    AddItemToInventory(request: AddItemToInventoryRequest): Observable<AddItemToInventoryResponse>;
    RemoveItemFromInventory(request: RemoveItemFromInventoryRequest): Observable<RemoveItemFromInventoryResponse>;
    EquipItem(request: EquipItemRequest): Observable<EquipItemResponse>;
    UnequipItem(request: UnequipItemRequest): Observable<UnequipItemResponse>;
    GetEquipmentStatBonus(request: GetEquipmentStatBonusRequest): Observable<GetEquipmentStatBonusResponse>;
}