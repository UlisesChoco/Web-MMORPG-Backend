import { Observable } from "rxjs";
import { GetFatalCombatReplayRequest } from "../interfaces/fatal-combat-replay/request/get-fatal-combat-replay-request.interface";
import { GetFatalCombatReplayResponse } from "../interfaces/fatal-combat-replay/response/get-fatal-combat-replay-response.interface";
import { GetRecentFatalitiesResponse } from "../interfaces/fatal-combat-replay/response/get-recent-fatalities-response.interface";
import { GetRecentFatalitiesRequest } from "../interfaces/fatal-combat-replay/request/get-recent-fatalities-request.interface";

export interface FatalCombatReplayService {
    GetFatalCombatReplay(request: GetFatalCombatReplayRequest): Observable<GetFatalCombatReplayResponse>;
    GetRecentFatalities(request: GetRecentFatalitiesRequest): Observable<GetRecentFatalitiesResponse>;
}