import { Observable } from "rxjs";
import { ListNPCVendorItemsRequest } from "../interfaces/npc_item/request/list-npc-vendor-items-request.interface";
import { ListNPCVendorItemsResponse } from "../interfaces/npc_item/response/list-npc-vendor-items-response.interface";

export interface NPCItemService {
  ListNPCVendorItems(request: ListNPCVendorItemsRequest): Observable<ListNPCVendorItemsResponse>;
}