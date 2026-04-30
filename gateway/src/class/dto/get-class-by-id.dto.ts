export interface GetClassByIdDTO {
    message: string;
    data: ClassDataDTO;
}

export interface ClassDataDTO {
    id: number;
    name: string;
    description: string;
    critRate: number;
    critDamage: number;
    hp: number;
    atk: number;
    def: number;
    stamina: number;
    accuracy: number;
    evasion: number;
}