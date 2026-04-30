import { Long } from "@grpc/proto-loader";
import { ProcessCombatResponseDTO } from "../dto/combat/process-combat-response.dto";
import { TurnActionGrpc, TurnResultGrpc, type ProcessCombatResponse } from "../interfaces/combat/response/process-combat-response.interface";
import { Util } from "src/common/util/number.functions";

export class CombatMapper {
    static toProcessCombatResponseDTO(data: any): ProcessCombatResponseDTO {
        const response = data as ProcessCombatResponse;

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

        const mapped: ProcessCombatResponseDTO = {
            combatId: Util.longToNumber(response.combatId),
            wasFatal: response.wasFatal ?? false,
            totalTurns: response.totalTurns,
            turns: turnsMapped,
            loot: {
                gold: response.loot.gold ?? 0,
                experience: response.loot.experience ?? 0,
                itemId: response.loot.itemId?.toNumber() ?? undefined,
            },
        };

        return mapped;
    }
}