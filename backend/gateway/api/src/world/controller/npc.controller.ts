import { Controller, Get, Inject, Logger, OnModuleInit, Param, Res } from "@nestjs/common";
import { NPCService } from "../service/npc.service";
import type { ClientGrpc } from "@nestjs/microservices/interfaces/client-grpc.interface";
import type { Response } from "express";
import { Http } from "src/common/http/http.handle.response";
import { firstValueFrom } from "rxjs";
import { NPCMapper } from "../mapper/npc.mapper";

@Controller('npc')
export class NpcController implements OnModuleInit {
    private npcService: NPCService;
    private readonly logger = new Logger(NpcController.name);

    constructor(@Inject('NPC_PACKAGE') private readonly client: ClientGrpc) { }

    onModuleInit() {
        this.npcService = this.client.getService<NPCService>('NPCService');
    }

    @Get(':id')
    async getNPCById(
        @Param() params: any,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                npcId: params.id
            };

            const observable = this.npcService.GetNPCById(request);

            const data = await firstValueFrom(observable);

            const finalData = NPCMapper.toGetNPCByIdResponseDTO(data);

            this.logger.log(`NPC obtenido por ID: ${JSON.stringify(data)}`);

            return finalData;
        } catch (err) {
            this.logger.error(`Error al obtener NPC por ID: ${err.message}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }

    @Get()
    async listNPCs(
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = { };

            const observable = this.npcService.ListNPCs(request);

            const data = await firstValueFrom(observable);

            const finalData = NPCMapper.toListNPCsResponseDTO(data)

            this.logger.log(`Lista de NPCs obtenida: ${finalData}`);

            return finalData;
        } catch (err) {
            this.logger.error(`Error al listar NPCs: ${err.message}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }
}