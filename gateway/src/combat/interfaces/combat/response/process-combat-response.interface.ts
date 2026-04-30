export interface ProcessCombatResponse {
    combatId: Long;
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
    turnAction: TurnActionGrpc;
    turnResult: TurnResultGrpc;
    damage: Long;
    critical: boolean;
}

export enum TurnActionGrpc {
    TURN_ACTION_UNSPECIFIED = 0,
    BASIC_ATTACK = 1
}

export enum TurnResultGrpc {
    TURN_RESULT_UNSPECIFIED = 0,
    HIT = 1,
    MISS = 2,
    CRITICAL_HIT = 3,
    DODGE = 4
}

export interface StateGrpc {
    hp: Long;
    stamina: Long;
    accuracy: Long;
    evasion: Long;
}

export interface LootGrpc {
    gold: number;
    experience: number;
    itemId: Long | null;
}