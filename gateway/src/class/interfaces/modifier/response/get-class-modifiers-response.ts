export interface GetClassModifiersResponse {
    message: string;
    modifiers: ModifierData;
}

export interface ModifierData {
    id: string;
    critRateModifier: number;
    critDamageModifier: number;
    hpModifier: number;
    atkModifier: number;
    defModifier: number;
    staminaModifier: number;
    accuracyModifier: number;
    evasionModifier: number;
}