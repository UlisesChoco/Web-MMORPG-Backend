import { CombatTurnGrpc } from "../combat/process-combat-response.dto";

export interface GetFatalCombatReplayDTO {
    turns: Array<CombatTurnGrpc>;
}