export class GetTowerFloorResponseDTO {
  tower: TowerDTO;
}

export class TowerDTO {
  id: number;
  floor: number;
  levelRange: string;
}