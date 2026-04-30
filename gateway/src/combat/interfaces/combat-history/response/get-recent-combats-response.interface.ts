import { CombatHistory } from "./get-combat-history-by-player-response.interface";

export interface GetRecentCombatsResponse {
    combats: Array<CombatHistory>;
}