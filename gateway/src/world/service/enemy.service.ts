import { Observable } from "rxjs";
import { GetEnemyByIdRequest } from "../interfaces/enemy/request/get-enemy-by-id-request.interface";
import { GetEnemyResponse } from "../interfaces/enemy/response/get-enemy-by-id-response.interface";

export interface EnemyService {
    GetEnemyById(request: GetEnemyByIdRequest): Observable<GetEnemyResponse>;
}