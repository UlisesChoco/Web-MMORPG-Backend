export interface GetTowerFloorResponse {
  tower: Tower;
}

export interface Tower {
  id: Long;
  floor: Long;
  levelRange: string;
}