import { Controller, Get, HttpCode, HttpStatus, Inject, Logger, OnModuleInit, Param, Res } from "@nestjs/common";
import { CombatHistoryService } from "../service/combat-history.service";
import type { ClientGrpc } from "@nestjs/microservices";
import type { Response } from "express";
import { Http } from "src/common/http/http.handle.response";
import { firstValueFrom } from "rxjs";
import { CombatHistoryMapper } from "../mapper/combat-history.mapper";

@Controller('combat-history')
export class CombatHistoryController implements OnModuleInit {
    private combatHistoryService: CombatHistoryService;
    private readonly logger = new Logger(CombatHistoryController.name);

    constructor(@Inject('COMBAT_HISTORY_PACKAGE') private readonly client: ClientGrpc) {}

    onModuleInit() {
        this.combatHistoryService = this.client.getService<CombatHistoryService>('CombatHistoryService');
    }

    @Get('by-player/:id')
    @HttpCode(HttpStatus.OK)
    async getCombatHistoryByPlayer(
        @Param() params: any,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                playerId: params.id
            }

            const observable = this.combatHistoryService.GetCombatHistoryByPlayer(request);

            const data = await firstValueFrom(observable);

            this.logger.log(`Historial de combate obtenido para el jugador de ID ${params.id}`);

            return CombatHistoryMapper.toGetCombatHistoryByPlayerDTO(data);
        } catch (err) {
            this.logger.error(`Error al obtener el historial de combate del jugador ${params.id}: ${err}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }

    @Get('recent/:limit')
    @HttpCode(HttpStatus.OK)
    async getRecentCombats(
        @Param() params: any,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                limit: params.limit
            }

            const observable = this.combatHistoryService.GetRecentCombats(request);

            const data = await firstValueFrom(observable);

            this.logger.log(`Obtenidos los ${params.limit} combates recientes`);

            return CombatHistoryMapper.toGetRecentCombatsDTO(data);
        } catch (err) {
            this.logger.error(`Error al obtener los combates recientes: ${err}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }

    @Get('win-count/:playerId')
    @HttpCode(HttpStatus.OK)
    async getPlayerWinCount(
        @Param() params: any,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                playerId: params.playerId
            }

            const observable = this.combatHistoryService.GetPlayerWinCount(request);

            const data = await firstValueFrom(observable);

            this.logger.log(`Obtenido el conteo de victorias para el jugador de ID ${params.playerId}`);

            return CombatHistoryMapper.toGetPlayerWinCountDTO(data);
        } catch (err) {
            this.logger.error(`Error al obtener el conteo de victorias del jugador ${params.playerId}: ${err}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }
}