import { Observable } from "rxjs";
import { ListTowerEnemiesResponse } from "../interfaces/tower_enemy/response/list-tower-enemies-response.interface";
import { ListTowerEnemiesRequest } from "../interfaces/tower_enemy/request/list-tower-enemies-request.interface";

export interface TowerEnemyService {
  ListTowerEnemies(request: ListTowerEnemiesRequest): Observable<ListTowerEnemiesResponse>;
}