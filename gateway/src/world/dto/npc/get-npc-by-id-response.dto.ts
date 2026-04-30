export class GetNPCResponseDTO {
  npc: NPCDTO;
}

export class NPCDTO {
  id: number;
  name: string;
  description: string;
  type: string;
}