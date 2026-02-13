import { Util } from "src/common/util/number.functions";
import { GetEnemyResponseDTO } from "../dto/enemy/get-enemy-by-id-response.dto";
import { EnemyType, GetEnemyResponse } from "../interfaces/enemy/response/get-enemy-by-id-response.interface";

export class EnemyMapper {
    static toGetEnemyByIdResponseDTO(data: any): GetEnemyResponseDTO {
        const response = data as GetEnemyResponse;

        const enemy = {
            id: Util.longToNumber(response.enemy.id),
            name: response.enemy.name,
            description: response.enemy.description,
            type: EnemyType[response.enemy.type],
            level: response.enemy.level,
            experience: response.enemy.experience,
            gold: response.enemy.gold,
            critRate: response.enemy.critRate,
            critDamage: response.enemy.critDamage,
            hp: response.enemy.hp,
            atk: response.enemy.atk,
            def: response.enemy.def,
            stamina: response.enemy.stamina,
            accuracy: response.enemy.accuracy,
            evasion: response.enemy.evasion,
        };

        const mapped = {
            enemy: enemy
        };

        return mapped;
    }
}