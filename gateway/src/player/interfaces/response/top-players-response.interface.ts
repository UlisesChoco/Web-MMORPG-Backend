export interface TopPlayersResponse {
  players: Array<PlayerSummary>;
}

export interface PlayerSummary {
  name: string;
  level: number;
}