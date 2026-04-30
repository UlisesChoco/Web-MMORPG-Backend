import { Util } from "src/common/util/number.functions";
import { ListNPCVendorItemsResponse } from "../interfaces/npc_item/response/list-npc-vendor-items-response.interface";
import { ListNPCVendorItemsResponseDTO } from "../dto/npc_item/list-npc-vendor-items-response.dto";

export class NPCItemMapper {
    static toListNPCVendorItemsResponseDTO(data: any): ListNPCVendorItemsResponseDTO {
        const response = data as ListNPCVendorItemsResponse;

        if(!response || !response.items) {
            return {
                items: []
            };
        }

        const items = response.items.map((item) => ({
            id: Util.longToNumber(item.id),
            itemId: Util.longToNumber(item.itemId),
            price: item.price
        }));

        const mapped = {
            items: items
        };

        return mapped;
    }
}