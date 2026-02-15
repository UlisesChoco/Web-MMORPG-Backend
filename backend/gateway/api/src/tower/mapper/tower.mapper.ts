import { Util } from "src/common/util/number.functions";
import { GetTowerFloorResponseDTO } from "../dto/tower/get-tower-floor-response.dto";
import { GetTowerFloorResponse } from "../interfaces/tower/response/get-tower-floor-response.interface";
import { ListTowerFloorsResponse } from "../interfaces/tower/response/list-tower-floors-response";
import { ListTowerFloorsResponseDTO } from "../dto/tower/list-tower-floors-response.dto";

export class TowerMapper {
    static toGetTowerFloorResponseDTO(data: any): GetTowerFloorResponseDTO {
        const response = data as GetTowerFloorResponse;

        const towerDTO = {
            id: Util.longToNumber(response.tower.id),
            floor: Util.longToNumber(response.tower.floor),
            levelRange: response.tower.levelRange
        };

        const mapped = {
            tower: towerDTO
        };

        return mapped;
    }

    static toListTowerFloorsResponseDTO(data: any): ListTowerFloorsResponseDTO {
        const response = data as ListTowerFloorsResponse;

        const floorsDTO = response.floors.map(floor => {
            return {
                id: Util.longToNumber(floor.id),
                floor: Util.longToNumber(floor.floor),
                levelRange: floor.levelRange
            }
        });

        const mapped = {
            floors: floorsDTO
        };

        return mapped;
    }
}