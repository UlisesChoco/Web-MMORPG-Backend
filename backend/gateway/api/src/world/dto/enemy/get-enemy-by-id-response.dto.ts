export class GetEnemyResponseDTO {
  enemy: EnemyDTO;
}

export class EnemyDTO {
  id: number;
  name: string;
  description: string;
  type: string;
  level: number;
  experience: number;
  gold: number;
  critRate: number;
  critDamage: number;
  hp: number;
  atk: number;
  def: number;
  stamina: number;
  accuracy: number;
  evasion: number;
}