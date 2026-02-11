import { Util } from "src/common/util/number.functions";
import { GetPlayerTowerProgressResponseDTO } from "../dto/tower_player_progress/get-player-tower-progress-response.dto";
import { GetPlayerTowerProgressResponse } from "../interfaces/tower_player_progress/response/get-player-tower-progress-response.interface";
import { RegisterPlayerTowerProgressRequest } from "../interfaces/tower_player_progress/request/register-player-tower-progress-request.interface";
import { RegisterPlayerTowerProgressRequestDTO } from "../dto/tower_player_progress/register-player-tower-progress-request.dto";

export class TowerPlayerProgressMapper {
    static toGetPlayerTowerProgressResponseDTO(data: any): GetPlayerTowerProgressResponseDTO {
        const response = data as GetPlayerTowerProgressResponse;

        const progress = {
            floor: Util.longToNumber(response.progress.floor),
            levelRange: response.progress.levelRange
        }

        const mapped = {
            progress: progress
        };

        return mapped;
    }

    static toRegisterPlayerTowerProgressRequest(dto: any): RegisterPlayerTowerProgressRequest {
        const response = dto as RegisterPlayerTowerProgressRequestDTO;

        const request = {
            playerId: response.playerId,
            towerId: response.towerId
        };

        return request;
    }
}