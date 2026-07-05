import { IsInt } from 'class-validator';

export class EquipItemRequestDTO {
  @IsInt()
  playerId: number;

  @IsInt()
  inventoryItemId: number;

  @IsInt()
  playerLevel: number;
}
