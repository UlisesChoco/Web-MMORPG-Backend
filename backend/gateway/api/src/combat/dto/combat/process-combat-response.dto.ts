export interface ProcessCombatResponseDTO {
    combatId: number;
    wasFatal: boolean;
    totalTurns: number;
    turns: Array<CombatTurnGrpc>;
    loot: LootGrpc;
}

export interface CombatTurnGrpc {
    turnNumber: number;
    playerAction: ActionGrpc;
    enemyAction: ActionGrpc;
    playerStateAfter: StateGrpc;
    enemyStateAfter: StateGrpc;
}

export interface ActionGrpc {
    turnAction: string;
    turnResult: string;
    damage: number;
    critical: boolean;
}

export interface StateGrpc {
    hp: number;
    stamina: number;
    accuracy: number;
    evasion: number;
}

export interface LootGrpc {
    gold: number;
    experience: number;
    itemId: number | undefined;
}