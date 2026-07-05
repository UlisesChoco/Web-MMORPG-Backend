import { IsInt } from 'class-validator';

export class AddItemToInventoryRequestDTO {
  @IsInt()
  playerId: number;

  @IsInt()
  itemId: number;
}
