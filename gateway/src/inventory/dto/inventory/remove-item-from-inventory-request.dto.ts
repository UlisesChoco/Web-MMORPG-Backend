import { IsInt } from 'class-validator';

export class RemoveItemFromInventoryRequestDTO {
  @IsInt()
  playerId: number;

  @IsInt()
  inventoryItemId: number;
}
