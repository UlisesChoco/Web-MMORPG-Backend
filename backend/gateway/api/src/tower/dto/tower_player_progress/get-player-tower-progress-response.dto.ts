export class GetPlayerTowerProgressResponseDTO {
    progress: TowerPlayerProgressDTO;
}

export class TowerPlayerProgressDTO {
    floor: number;
    levelRange: string;
}