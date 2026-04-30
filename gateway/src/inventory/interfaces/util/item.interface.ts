export interface Item {
    inventoryItemId: Long;
    name: string;
    description: string;
    gold: Long;
    requiredLevel: Long;
    type: ItemType;
    slot: SlotType;
    hpBonus: Long;
    atkBonus: Long;
    defBonus: Long;
    staminaBonus: Long;
    accuracyBonus: Long;
    evasionBonus: Long;
    critRateBonus: number;
    critDamageBonus: number;
    equipped: boolean;
}

export enum ItemType {
    ITEM_TYPE_UNSPECIFIED = 0,
    WEAPON = 1,
    ARMOR = 2
}

export enum SlotType {
  SLOT_TYPE_UNSPECIFIED = 0,
  HEAD = 1,
  CHEST = 2,
  LEGS = 3,
  FEET = 4,
  HANDS = 5,
  MAIN_HAND = 6,
  OFF_HAND = 7,
  RING = 8,
  NECKLACE = 9
}