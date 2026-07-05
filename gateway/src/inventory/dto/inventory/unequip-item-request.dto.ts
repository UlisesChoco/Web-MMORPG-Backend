import { IsInt } from 'class-validator';

export class UnequipItemRequestDTO {
  @IsInt()
  playerId: number;

  @IsInt()
  inventoryItemId: number;
}
