import { Observable } from "rxjs";
import { GetItemsByRequiredLevelAndSlotRequest } from "../interfaces/item/request/get-items-by-required-level-and-slot-request.interface";
import { GetItemsByRequiredLevelAndSlotResponse } from "../interfaces/item/response/get-items-by-required-level-and-slot-response.interface";
import { GetItemsByRequiredLevelRequest } from "../interfaces/item/request/get-items-by-required-level-request.interface";
import { GetItemsByRequiredLevelResponse } from "../interfaces/item/response/get-items-by-required-level-response.interface";

export interface ItemService {
    GetItemsByRequiredLevel(request: GetItemsByRequiredLevelRequest): Observable<GetItemsByRequiredLevelResponse>;
    GetItemsByRequiredLevelAndSlot(request: GetItemsByRequiredLevelAndSlotRequest): Observable<GetItemsByRequiredLevelAndSlotResponse>;
}