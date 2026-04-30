import { Observable } from "rxjs";
import { GetNPCByIdRequest } from "../interfaces/npc/request/get-npc-by-id-request.interface";
import { ListNPCsRequest } from "../interfaces/npc/request/list-npcs-request.interface";
import { GetNPCResponse } from "../interfaces/npc/response/get-npc-by-id-response.interface";
import { ListNPCsResponse } from "../interfaces/npc/response/list-npcs-response.interface";

export interface NPCService {
  GetNPCById(request: GetNPCByIdRequest): Observable<GetNPCResponse>;

  ListNPCs(request: ListNPCsRequest): Observable<ListNPCsResponse>;
}