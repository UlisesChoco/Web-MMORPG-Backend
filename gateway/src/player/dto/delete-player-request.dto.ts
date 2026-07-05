import { IsInt } from 'class-validator';

export class DeletePlayerRequestDTO {
  @IsInt()
  playerId: number;
}
