import { CombatTurnGrpc } from "../../combat/response/process-combat-response.interface";

export interface GetFatalCombatReplayResponse {
    turns: Array<CombatTurnGrpc>;
}