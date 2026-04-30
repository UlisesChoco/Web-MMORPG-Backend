export interface GetEnemyDropTableResponse {
    dropTable: Array<LootItem>;
}

export interface LootItem {
    itemId: number;
    dropChance: number;
}