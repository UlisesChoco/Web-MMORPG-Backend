import { IsInt } from 'class-validator';

export class RegisterPlayerTowerProgressRequestDTO {
  @IsInt()
  towerId: number;

  @IsInt()
  playerId: number;
}
