export interface GetNPCResponse {
  npc: NPC;
}

export interface NPC {
  id: Long;
  name: string;
  description: string;
  type: NPCType;
}

export enum NPCType {
  NPC_TYPE_UNSPECIFIED = 0,
  MERCHANT = 1,
  QUEST_GIVER = 2,
  TRAINER = 3,
  BANKER = 4,
  BLACKSMITH = 5
}