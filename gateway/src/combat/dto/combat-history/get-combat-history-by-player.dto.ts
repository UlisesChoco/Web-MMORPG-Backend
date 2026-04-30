export interface GetCombatHistoryByPlayerDTO {
    combats: Array<CombatHistory>;
}

export interface CombatHistory {
    enemyId: number;
    wasFatal: boolean;
    date: string;
    totalTurns: number;
}