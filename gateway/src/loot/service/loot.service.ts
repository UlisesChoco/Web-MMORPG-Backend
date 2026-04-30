import { Observable } from "rxjs";
import { GetEnemyDropTableRequest } from "../interfaces/request/get-enemy-drop-table-request.interface";
import { GetEnemyDropTableResponse } from "../interfaces/response/get-enemy-drop-table-response.interface";

export interface LootService {
    GetEnemyDropTable(request: GetEnemyDropTableRequest): Observable<GetEnemyDropTableResponse>;
}