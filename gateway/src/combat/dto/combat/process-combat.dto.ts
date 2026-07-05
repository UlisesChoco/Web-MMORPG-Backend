import { IsInt } from 'class-validator';

export class ProcessCombatDTO {
  @IsInt()
  playerId: number;

  @IsInt()
  enemyId: number;
}
