export class ListMapEnemiesResponseDTO {
  pool: Array<MapEnemyDTO>;
}

export class MapEnemyDTO {
  id: number;
  name: string;
  description: string;
  gold: number;
}