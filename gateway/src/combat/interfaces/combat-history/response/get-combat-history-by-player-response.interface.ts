export interface GetCombatHistoryByPlayerResponse {
    combats: Array<CombatHistory>;
}

export interface CombatHistory {
    enemyId: Long;
    wasFatal: boolean;
    date: string;
    totalTurns: number;
}