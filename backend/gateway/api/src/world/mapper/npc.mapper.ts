import { Util } from "src/common/util/number.functions";
import { GetNPCResponse, NPCType } from "../interfaces/npc/response/get-npc-by-id-response.interface";
import { GetNPCResponseDTO } from "../dto/npc/get-npc-by-id-response.dto";
import { ListNPCsResponseDTO } from "../dto/npc/list-npcs-response.dto";
import { ListNPCsResponse } from "../interfaces/npc/response/list-npcs-response.interface";

export class NPCMapper {
    static toGetNPCByIdResponseDTO(data: any): GetNPCResponseDTO {
        const response = data as GetNPCResponse;

        const npc = {
            id: Util.longToNumber(response.npc.id),
            name: response.npc.name,
            description: response.npc.description,
            type: NPCType[response.npc.type]
        };

        const mapped = {
            npc: npc
        };

        return mapped;
    }

    static toListNPCsResponseDTO(data: any): ListNPCsResponseDTO {
        const response = data as ListNPCsResponse;

        const npcs = response.npcs.map(npc => {
            return {
                id: Util.longToNumber(npc.id),
                name: npc.name,
                description: npc.description,
                type: NPCType[npc.type]
            }
        });

        const mapped = {
            npcs: npcs
        };

        return mapped;
    }
}