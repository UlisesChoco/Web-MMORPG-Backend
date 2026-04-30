import { Body, Controller, Get, Inject, Logger, OnModuleInit, Param, Post, Res } from "@nestjs/common";
import { TowerPlayerProgressService } from "../service/tower-player-progress.service";
import type { ClientGrpc } from "@nestjs/microservices";
import type { Response } from "express";
import { Http } from "src/common/http/http.handle.response";
import { firstValueFrom } from "rxjs";
import { TowerPlayerProgressMapper } from "../mapper/tower-player-progress.mapper";
import { RegisterPlayerTowerProgressRequestDTO } from "../dto/tower_player_progress/register-player-tower-progress-request.dto";

@Controller('tower-player-progress')
export class TowerPlayerProgressController implements OnModuleInit {
    private TowerPlayerProgressService: TowerPlayerProgressService;
    private readonly logger = new Logger(TowerPlayerProgressController.name);

    constructor(@Inject('TOWER_PLAYER_PROGRESS_PACKAGE') private readonly client: ClientGrpc) {}

    onModuleInit() {
        this.TowerPlayerProgressService = this.client.getService<TowerPlayerProgressService>('TowerPlayerProgressService');
    }

    @Get(':playerId')
    async getPlayerTowerProgress(
        @Param() params: any,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                playerId: params.playerId
            };

            const observable = this.TowerPlayerProgressService.GetPlayerTowerProgress(request);

            const data = await firstValueFrom(observable);

            const finalData = TowerPlayerProgressMapper.toGetPlayerTowerProgressResponseDTO(data);

            this.logger.log(`Progreso de la torre para el jugador ${params.playerId} obtenido exitosamente.`);

            return finalData;
        } catch (err) {
            this.logger.error(`Error al obtener el progreso de la torre para el jugador ${params.playerId}: ${err.message}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }

    @Post('register')
    async registerPlayerTowerProgress(
        @Body() dto: RegisterPlayerTowerProgressRequestDTO,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = TowerPlayerProgressMapper.toRegisterPlayerTowerProgressRequest(dto);

            const observable = this.TowerPlayerProgressService.RegisterPlayerTowerProgress(request);

            const data = await firstValueFrom(observable);

            this.logger.log(`Progreso de la torre para el jugador ${dto.playerId} registrado exitosamente.`);

            return data;
        } catch (err) {
            this.logger.error(`Error al registrar el progreso de la torre para el jugador ${dto.playerId}: ${err.message}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }
}