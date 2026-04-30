export interface PlayerResponseDTO {
    player: PlayerDTO;
}

export interface PlayerDTO {
    id: number;
    userId: number;
    classId: number;
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