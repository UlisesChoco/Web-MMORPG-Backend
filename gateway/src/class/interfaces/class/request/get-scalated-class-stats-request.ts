export interface GetScaledClassStatsRequest {
    classId: number;
    bonus: BonusStats;
    level: number;
}

export interface BonusStats {
    hp: number;
    atk: number;
    def: number;
    stamina: number;
    accuracy: number;
    evasion: number;
}