import { Util } from "src/common/util/number.functions";
import { ListTowerEnemiesResponseDTO } from "../dto/tower_enemy/list-tower-enemies-response.dto";
import { ListTowerEnemiesResponse } from "../interfaces/tower_enemy/response/list-tower-enemies-response.interface";

export class TowerEnemyMapper {
    static toListTowerEnemiesResponseDTO(data: any): ListTowerEnemiesResponseDTO {
        const response = data as ListTowerEnemiesResponse;

        if(!response.enemies) {
            return {
                enemies: []
            };
        }

        const enemies = response.enemies.map(enemy => {
            return {
                enemyId: Util.longToNumber(enemy.enemyId)
            }
        });

        const mapped = {
            enemies: enemies
        };

        return mapped;
    }
}