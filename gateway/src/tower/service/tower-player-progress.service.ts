import { Observable } from "rxjs";
import { GetPlayerTowerProgressRequest } from "../interfaces/tower_player_progress/request/get-player-tower-progress-request.interface";
import { RegisterPlayerTowerProgressRequest } from "../interfaces/tower_player_progress/request/register-player-tower-progress-request.interface";
import { GetPlayerTowerProgressResponse } from "../interfaces/tower_player_progress/response/get-player-tower-progress-response.interface";
import { RegisterPlayerTowerProgressResponse } from "../interfaces/tower_player_progress/response/register-player-tower-progress-response.interface";

export interface TowerPlayerProgressService {
  GetPlayerTowerProgress(request: GetPlayerTowerProgressRequest): Observable<GetPlayerTowerProgressResponse>;
  RegisterPlayerTowerProgress(request: RegisterPlayerTowerProgressRequest): Observable<RegisterPlayerTowerProgressResponse>;
}