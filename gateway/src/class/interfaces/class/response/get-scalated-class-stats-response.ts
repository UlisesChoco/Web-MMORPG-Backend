export interface GetScaledClassStatsResponse {
    message: string;
    scalatedStats: ScaledStats;
}

export interface ScaledStats {
    hp: number;
    atk: number;
    def: number;
    stamina: number;
    accuracy: number;
    evasion: number;
    critRate: number;
    critDamage: number;
}