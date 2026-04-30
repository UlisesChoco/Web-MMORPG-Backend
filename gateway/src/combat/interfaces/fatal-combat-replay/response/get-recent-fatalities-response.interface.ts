export interface GetRecentFatalitiesResponse {
    fatalities: Array<FatalityEntry>;
}

export interface FatalityEntry {
    playerId: Long;
    enemyId: Long;
    date: string;
}