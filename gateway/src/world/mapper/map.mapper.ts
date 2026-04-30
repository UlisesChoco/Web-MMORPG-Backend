import { Util } from "src/common/util/number.functions";
import { GetMapResponseDTO, MapDTO } from "../dto/map/get-map-response.dto";
import { GetMapResponse } from "../interfaces/map/response/get-map-response.interface";
import { ListMapsResponse } from "../interfaces/map/response/list-maps-response.interface";
import { ListMapsResponseDTO } from "../dto/map/list-maps-response.dto";
import { ListMapEnemiesResponseDTO } from "../dto/map/list-map-enemies-response.dto";
import { ListMapEnemiesResponse } from "../interfaces/map/response/list-map-enemies-response.interface";
import { EnemyType } from "../interfaces/enemy/response/get-enemy-by-id-response.interface";
import { GetExpandedMapEnemyPoolResponseDTO } from "../dto/map/get-expanded-map-enemy-pool-response.dto";
import { GetExpandedMapEnemyPoolResponse } from "../interfaces/map/response/get-expanded-map-enemy-pool-response.interface";

export class MapMapper {
    static toGetMapResponseDTO(data: any): GetMapResponseDTO {
        const response = data as GetMapResponse;

        const map = {
            id: Util.longToNumber(response.map.id),
            name: response.map.name,
            description: response.map.description,
            rangeLevel: response.map.rangeLevel
        };

        const mapped = {
            map: map
        };

        return mapped;
    }

    static toListMapsResponseDTO(data: any): ListMapsResponseDTO {
        const response = data as ListMapsResponse;

        if(!response.maps || response.maps.length === 0) {
            return {
                maps: []
            };
        }

        const maps = response.maps.map(map => {
            return this.toGetMapResponseDTO({ map: map });
        });

        const mapped = {
            maps: maps
        }

        return mapped;
    }

    static toListMapEnemiesResponseDTO(data: any): ListMapEnemiesResponseDTO {
        const response = data as ListMapEnemiesResponse;

        if(!response.pool || response.pool.length === 0) {
            return {
                pool: []
            };
        }

        const pool = response.pool.map(enemy => {
            return {
                id: Util.longToNumber(enemy.id),
                name: enemy.name,
                description: enemy.description,
                gold: enemy.gold
            }
        });

        const mapped = {
            pool: pool
        };

        return mapped;
    }

    static toGetExpandedMapEnemyPoolResponseDTO(data: any): GetExpandedMapEnemyPoolResponseDTO {
        const response = data as GetExpandedMapEnemyPoolResponse;

        if(!response.enemies || response.enemies.length === 0) {
            return {
                enemies: []
            };
        }

        const enemies = response.enemies.map(enemy => {
            return {
                id: Util.longToNumber(enemy.id),
                name: enemy.name,
                description: enemy.description,
                type: EnemyType[enemy.type],
                level: enemy.level,
                experience: enemy.experience,
                gold: enemy.gold,
                critRate: enemy.critRate,
                critDamage: enemy.critDamage,
                hp: enemy.hp,
                atk: enemy.atk,
                def: enemy.def,
                stamina: enemy.stamina,
                accuracy: enemy.accuracy,
                evasion: enemy.evasion
            }
        });

        const mapped = {
            enemies: enemies
        };

        return mapped;
    }
}