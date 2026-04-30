export class UpdatePlayerRequestDTO {
    playerId: Long;
    updates: PlayerUpdateDataDTO;
}

export class PlayerUpdateDataDTO {
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