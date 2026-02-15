export interface GetEnemyResponse {
  enemy: Enemy;
}

export interface Enemy {
  id: Long;
  name: string;
  description: string;
  type: EnemyType;
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

export enum EnemyType {
  ENEMY_TYPE_UNSPECIFIED = 0,
  NORMAL = 1,
  ELITE = 2,
  BOSS = 3,
  MINIBOSS = 4,
  LEGENDARY = 5,
}