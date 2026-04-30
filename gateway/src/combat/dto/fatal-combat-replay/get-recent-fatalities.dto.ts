export interface GetRecentFatalitiesDTO {
    fatalities: Array<FatalityEntryDTO>;
}

export interface FatalityEntryDTO {
    playerId: number;
    enemyId: number;
    date: string;
}