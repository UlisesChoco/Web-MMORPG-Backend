export interface GetPlayerTowerProgressResponse {
    progress: TowerPlayerProgress
}

export interface TowerPlayerProgress {
    floor: Long;
    levelRange: string;
}