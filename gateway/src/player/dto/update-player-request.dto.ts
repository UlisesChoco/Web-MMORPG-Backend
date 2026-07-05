import { IsInt, IsOptional, IsString, ValidateNested } from 'class-validator';
import { Type } from 'class-transformer';

export class UpdatePlayerRequestDTO {
  @IsInt()
  playerId: number;

  @ValidateNested()
  @Type(() => PlayerUpdateDataDTO)
  updates: PlayerUpdateDataDTO;
}

export class PlayerUpdateDataDTO {
  @IsOptional()
  @IsString()
  name?: string;

  @IsOptional()
  @IsInt()
  gold?: number;

  @IsOptional()
  @IsInt()
  level?: number;

  @IsOptional()
  @IsInt()
  experience?: number;

  @IsOptional()
  @IsInt()
  experienceLimit?: number;

  @IsOptional()
  @IsInt()
  freeStatPoints?: number;

  @IsOptional()
  @IsInt()
  hpBonus?: number;

  @IsOptional()
  @IsInt()
  atkBonus?: number;

  @IsOptional()
  @IsInt()
  defBonus?: number;

  @IsOptional()
  @IsInt()
  staminaBonus?: number;

  @IsOptional()
  @IsInt()
  accuracyBonus?: number;

  @IsOptional()
  @IsInt()
  evasionBonus?: number;
}
