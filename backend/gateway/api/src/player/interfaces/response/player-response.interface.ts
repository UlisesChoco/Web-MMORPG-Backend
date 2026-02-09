export interface PlayerResponse {
    player: Player;
}

export interface Player {
    id: Long;
    userId: Long;
    classId: Long;
    name: string;
    alive: boolean;
    gold: number;
    level: number;
    experience: number;
    experienceLimit: number;
    freeStatPoints: number;
    hpBonus: number;
    atkBonus: number;
    defBonus: number;
    staminaBonus: number;
    accuracyBonus: number;
    evasionBonus: number;
}