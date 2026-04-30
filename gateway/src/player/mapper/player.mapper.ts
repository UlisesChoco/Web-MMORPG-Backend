import { Util } from "src/common/util/number.functions";
import { PlayerResponseDTO } from "../dto/player-response.dto";
import { PlayerResponse } from "../interfaces/response/player-response.interface";
import { TopPlayersResponseDTO } from "../dto/top-players-response.dto";
import { TopPlayersResponse } from "../interfaces/response/top-players-response.interface";
import { UpdatePlayerRequest } from "../interfaces/request/update-player-request.interface";
import { UpdatePlayerRequestDTO } from "../dto/update-player-request.dto";

export class PlayerMapper {
    static toPlayerResponseDTO(data: any): PlayerResponseDTO {
        const response = data as PlayerResponse;

        const playerDTO = {
            id: Util.longToNumber(response.player.id),
            userId: Util.longToNumber(response.player.userId),
            classId: Util.longToNumber(response.player.classId),
            name: response.player.name,
            alive: response.player.alive,
            gold: response.player.gold,
            level: response.player.level,
            experience: response.player.experience,
            experienceLimit: response.player.experienceLimit,
            freeStatPoints: response.player.freeStatPoints,
            hpBonus: response.player.hpBonus,
            atkBonus: response.player.atkBonus,
            defBonus: response.player.defBonus,
            staminaBonus: response.player.staminaBonus,
            accuracyBonus: response.player.accuracyBonus,
            evasionBonus: response.player.evasionBonus
        };

        const mapped = {
            player: playerDTO
        };

        return mapped;
    }

    static toTopPlayersResponseDTO(data: any): TopPlayersResponseDTO {
        const response = data as TopPlayersResponse;

        if(!response.players) {
            return {
                players: []
            };
        }

        const players = response.players.map(player => {
            return {
                name: player.name,
                level: player.level
            }
        });

        const mapped = {
            players: players
        };

        return mapped;
    }

    static toUpdatePlayerRequest(data: any): UpdatePlayerRequest {
        const dto = data as UpdatePlayerRequestDTO;

        const updates = {};

        if(dto.updates.name !== undefined) updates['name'] = dto.updates.name;
        if(dto.updates.gold !== undefined) updates['gold'] = dto.updates.gold;
        if(dto.updates.level !== undefined) updates['level'] = dto.updates.level;
        if(dto.updates.experience !== undefined) updates['experience'] = dto.updates.experience;
        if(dto.updates.experienceLimit !== undefined) updates['experienceLimit'] = dto.updates.experienceLimit;
        if(dto.updates.freeStatPoints !== undefined) updates['freeStatPoints'] = dto.updates.freeStatPoints;
        if(dto.updates.hpBonus !== undefined) updates['hpBonus'] = dto.updates.hpBonus;
        if(dto.updates.atkBonus !== undefined) updates['atkBonus'] = dto.updates.atkBonus;
        if(dto.updates.defBonus !== undefined) updates['defBonus'] = dto.updates.defBonus;
        if(dto.updates.staminaBonus !== undefined) updates['staminaBonus'] = dto.updates.staminaBonus;
        if(dto.updates.accuracyBonus !== undefined) updates['accuracyBonus'] = dto.updates.accuracyBonus;
        if(dto.updates.evasionBonus !== undefined) updates['evasionBonus'] = dto.updates.evasionBonus;

        const request: UpdatePlayerRequest = {
            playerId: dto.playerId,
            updates: updates
        };

        return request;
    }
}