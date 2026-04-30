import { Observable } from "rxjs";
import { GetCombatHistoryByPlayerRequest } from "../interfaces/combat-history/request/get-combat-history-by-player-request.interface";
import { GetCombatHistoryByPlayerResponse } from "../interfaces/combat-history/response/get-combat-history-by-player-response.interface";
import { GetRecentCombatsRequest } from "../interfaces/combat-history/request/get-recent-combats-request.interface";
import { GetRecentCombatsResponse } from "../interfaces/combat-history/response/get-recent-combats-response.interface";
import { GetPlayerWinCountRequest } from "../interfaces/combat-history/request/get-player-win-count-request.interface";
import { GetPlayerWinCountResponse } from "../interfaces/combat-history/response/get-player-win-count-response.interface";

export interface CombatHistoryService {
    GetCombatHistoryByPlayer(request: GetCombatHistoryByPlayerRequest): Observable<GetCombatHistoryByPlayerResponse>;
    GetRecentCombats(request: GetRecentCombatsRequest): Observable<GetRecentCombatsResponse>;
    GetPlayerWinCount(request: GetPlayerWinCountRequest): Observable<GetPlayerWinCountResponse>;
}