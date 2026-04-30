export interface UpdatePlayerRequest {
  playerId: Long;
  updates: PlayerUpdateData;
}

export interface PlayerUpdateData {
  name?: string;
  gold?: number;
  level?: number;
  experience?: number;
  experienceLimit?: number;
  freeStatPoints?: number;
  hpBonus?: number;
  atkBonus?: number;
  defBonus?: number;
  staminaBonus?: number;
  accuracyBonus?: number;
  evasionBonus?: number;
}