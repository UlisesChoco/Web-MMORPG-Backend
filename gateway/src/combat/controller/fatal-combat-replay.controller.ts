import { Controller, Get, HttpCode, HttpStatus, Inject, Logger, OnModuleInit, Param, Res } from "@nestjs/common";
import { FatalCombatReplayService } from "../service/fatal-combat-replay.service";
import type { ClientGrpc } from "@nestjs/microservices";
import type { Response } from "express";
import { Http } from "src/common/http/http.handle.response";
import { firstValueFrom } from "rxjs";
import { FatalCombatReplayMapper } from "../mapper/fatal-combat-replay.mapper";

@Controller('fatal-combat-replay')
export class FatalCombatReplayController implements OnModuleInit {
    private fatalCombatReplayService: FatalCombatReplayService;
    private readonly logger = new Logger(FatalCombatReplayController.name);

    constructor(@Inject('FATAL_COMBAT_REPLAY_PACKAGE') private readonly client: ClientGrpc) {}

    onModuleInit() {
        this.fatalCombatReplayService = this.client.getService<FatalCombatReplayService>('FatalCombatReplayService');
    }

    @Get(':id')
    @HttpCode(HttpStatus.OK)
    async getFatalCombatReplay(
        @Param() params: any,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                combatId: params.id
            }

            const observable = this.fatalCombatReplayService.GetFatalCombatReplay(request);

            const data = await firstValueFrom(observable);

            this.logger.log(`Obtenido replay de combate fatal con ID: ${params.id}`);

            return FatalCombatReplayMapper.toGetFatalCombatReplayDTO(data);
        } catch (err) {
            this.logger.error(`Error al obtener replay de combate fatal con ID: ${params.id}: ${err}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }

    @Get('recent-fatalities/:limit')
    @HttpCode(HttpStatus.OK)
    async getRecentFatalities(
        @Param() params: any,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                limit: params.limit
            }

            const observable = this.fatalCombatReplayService.GetRecentFatalities(request);

            const data = await firstValueFrom(observable);

            this.logger.log(`Obtenidas ${params.limit} muertes recientes`);

            return FatalCombatReplayMapper.toGetRecentFatalitiesDTO(data);
        } catch (err) {
            this.logger.error(`Error al obtener muertes recientes: ${err}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }
}