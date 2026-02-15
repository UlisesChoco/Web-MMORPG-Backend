import { Controller, Get, Inject, Logger, OnModuleInit, Param, Res } from "@nestjs/common";
import { NPCItemService } from "../service/npc-item.service";
import type { ClientGrpc } from "@nestjs/microservices";
import type { Response } from "express";
import { Http } from "src/common/http/http.handle.response";
import { firstValueFrom } from "rxjs";
import { NPCItemMapper } from "../mapper/npc-item.mapper";

@Controller('npc-item')
export class NpcItemController implements OnModuleInit {
    private npcItemService: NPCItemService;
    private readonly logger = new Logger(NpcItemController.name);

    constructor(@Inject('NPC_ITEM_PACKAGE') private readonly client: ClientGrpc) { }

    onModuleInit() {
        this.npcItemService = this.client.getService<NPCItemService>('NPCItemService');
    }

    @Get(':npcId')
    async listNPCVendorItems(
        @Param() params: any,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                npcId: params.npcId
            };

            const observable = this.npcItemService.ListNPCVendorItems(request);

            const data = await firstValueFrom(observable);

            const finalData = NPCItemMapper.toListNPCVendorItemsResponseDTO(data);

            this.logger.log(`Items del NPC con ID ${params.npcId} listados exitosamente`);

            return finalData;
        } catch (err) {
            this.logger.error(`Error al listar los items del NPC con ID ${params.npcId}: ${err.message}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }
}