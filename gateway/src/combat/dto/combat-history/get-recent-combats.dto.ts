import { CombatHistory } from "./get-combat-history-by-player.dto";

export interface GetRecentCombatsDTO {
    combats: Array<CombatHistory>;
}