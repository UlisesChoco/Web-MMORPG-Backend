import { Observable } from "rxjs";
import { TopPlayersRequest } from "../interfaces/request/top-players-request.interface";
import { GetPlayerByUserIdRequest } from "../interfaces/request/get-player-by-user-id-request.interface";
import { TopPlayersResponse } from "../interfaces/response/top-players-response.interface";
import { UpdatePlayerRequest } from "../interfaces/request/update-player-request.interface";
import { UpdatePlayerResponse } from "../interfaces/response/update-player-response.interface";
import { DeletePlayerRequest } from "../interfaces/request/delete-player-request.interface";
import { GetPlayerByIdRequest } from "../interfaces/request/get-player-by-id-request.interface";
import { DeletePlayerResponse } from "../interfaces/response/delete-player-response.interface";
import { PlayerResponse } from "../interfaces/response/player-response.interface";

export interface PlayerService {
    GetPlayerById(request: GetPlayerByIdRequest): Observable<PlayerResponse>;
    GetPlayerByUserId(request: GetPlayerByUserIdRequest): Observable<PlayerResponse>;
    GetTopPlayersByLevel(request: TopPlayersRequest): Observable<TopPlayersResponse>;
    UpdatePlayer(request: UpdatePlayerRequest): Observable<UpdatePlayerResponse>;
    DeletePlayer(request: DeletePlayerRequest): Observable<DeletePlayerResponse>;
}