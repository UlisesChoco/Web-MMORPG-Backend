import { Util } from "src/common/util/number.functions";
import { GetCombatHistoryByPlayerDTO } from "../dto/combat-history/get-combat-history-by-player.dto";
import { GetCombatHistoryByPlayerResponse } from "../interfaces/combat-history/response/get-combat-history-by-player-response.interface";
import { GetRecentCombatsDTO } from "../dto/combat-history/get-recent-combats.dto";
import { GetRecentCombatsResponse } from "../interfaces/combat-history/response/get-recent-combats-response.interface";
import { GetPlayerWinCountResponse } from "../interfaces/combat-history/response/get-player-win-count-response.interface";
import { GetPlayerWinCountDTO } from "../dto/combat-history/get-player-win-count.dto";

export class CombatHistoryMapper {
    static toGetCombatHistoryByPlayerDTO(data: any): GetCombatHistoryByPlayerDTO {
        const response = data as GetCombatHistoryByPlayerResponse;

        const combats = response.combats.map((combat) => ({
            enemyId: Util.longToNumber(combat.enemyId),
            wasFatal: combat.wasFatal ?? false,
            date: combat.date,
            totalTurns: combat.totalTurns
        }));

        const dto = {
            combats: combats
        };

        return dto;
    }

    static toGetRecentCombatsDTO(data: any): GetRecentCombatsDTO {
        const response = data as GetRecentCombatsResponse;

        const combats = response.combats.map((combat) => ({
            enemyId: Util.longToNumber(combat.enemyId),
            wasFatal: combat.wasFatal ?? false,
            date: combat.date,
            totalTurns: combat.totalTurns
        }));

        const dto = {
            combats: combats
        };

        return dto;
    }

    static toGetPlayerWinCountDTO(data: any): GetPlayerWinCountDTO {
        const response = data as GetPlayerWinCountResponse;

        const dto: GetPlayerWinCountDTO = {
            winCount: Util.longToNumber(response.winCount)
        };

        return dto;
    }
}