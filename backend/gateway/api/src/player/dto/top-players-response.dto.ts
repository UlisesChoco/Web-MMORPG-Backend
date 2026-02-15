export interface TopPlayersResponseDTO {
  players: Array<PlayerSummaryDTO>;
}

export interface PlayerSummaryDTO {
  name: string;
  level: number;
}