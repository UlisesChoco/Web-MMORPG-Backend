import { Util } from "src/common/util/number.functions";
import { GetFatalCombatReplayDTO } from "../dto/fatal-combat-replay/get-fatal-combat-replay.dto";
import { GetFatalCombatReplayResponse } from "../interfaces/fatal-combat-replay/response/get-fatal-combat-replay-response.interface";
import { TurnActionGrpc, TurnResultGrpc } from "../interfaces/combat/response/process-combat-response.interface";
import { GetRecentFatalitiesDTO } from "../dto/fatal-combat-replay/get-recent-fatalities.dto";
import { GetRecentFatalitiesResponse } from "../interfaces/fatal-combat-replay/response/get-recent-fatalities-response.interface";

export class FatalCombatReplayMapper {
    static toGetFatalCombatReplayDTO(data: any): GetFatalCombatReplayDTO {
        const response = data as GetFatalCombatReplayResponse;

        const turnsMapped = response.turns.map(turn => {
            return {
                turnNumber: turn.turnNumber,
                playerAction: {
                    turnAction: TurnActionGrpc[turn.playerAction.turnAction],
                    turnResult: TurnResultGrpc[turn.playerAction.turnResult],
                    damage: Util.longToNumber(turn.playerAction.damage),
                    critical: turn.playerAction.critical ?? false,
                },
                enemyAction: {
                    turnAction: TurnActionGrpc[turn.enemyAction.turnAction],
                    turnResult: TurnResultGrpc[turn.enemyAction.turnResult],
                    damage: Util.longToNumber(turn.enemyAction.damage),
                    critical: turn.enemyAction.critical ?? false,
                },
                playerStateAfter: {
                    hp: Util.longToNumber(turn.playerStateAfter.hp),
                    stamina: Util.longToNumber(turn.playerStateAfter.stamina),
                    accuracy: Util.longToNumber(turn.playerStateAfter.accuracy),
                    evasion: Util.longToNumber(turn.playerStateAfter.evasion),
                },
                enemyStateAfter: {
                    hp: Util.longToNumber(turn.enemyStateAfter.hp),
                    stamina: Util.longToNumber(turn.enemyStateAfter.stamina),
                    accuracy: Util.longToNumber(turn.enemyStateAfter.accuracy),
                    evasion: Util.longToNumber(turn.enemyStateAfter.evasion),
                },
            };
        });

        const dto = {
            turns: turnsMapped
        };

        return dto;
    }

    static toGetRecentFatalitiesDTO(data: any): GetRecentFatalitiesDTO {
        const response = data as GetRecentFatalitiesResponse;

        const fatalitiesMapped = response.fatalities.map(fatality => {
            return {
                playerId: Util.longToNumber(fatality.playerId),
                enemyId: Util.longToNumber(fatality.enemyId),
                date: fatality.date,
            }
        });

        const dto = {
            fatalities: fatalitiesMapped
        };

        return dto;
    }
}