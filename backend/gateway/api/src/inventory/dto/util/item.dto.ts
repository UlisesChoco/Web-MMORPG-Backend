export interface ItemDTO {
    inventoryItemId: number;
    name: string;
    description: string;
    gold: number;
    requiredLevel: number;
    type: string;
    slot: string;
    hpBonus: number;
    atkBonus: number;
    defBonus: number;
    staminaBonus: number;
    accuracyBonus: number;
    evasionBonus: number;
    critRateBonus: number;
    critDamageBonus: number;
    equipped: boolean;
}