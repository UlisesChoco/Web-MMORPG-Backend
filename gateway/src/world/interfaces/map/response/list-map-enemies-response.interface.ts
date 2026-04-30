export interface ListMapEnemiesResponse {
  pool: Array<MapEnemy>;
}

export interface MapEnemy {
  id: Long;
  name: string;
  description: string;
  gold: number;
}