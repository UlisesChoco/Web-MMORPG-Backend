import { Controller, Get, HttpCode, HttpStatus, Inject, Logger, OnModuleInit, Param, Query, Res } from "@nestjs/common";
import { PlayerClassService } from "../service/player-class.service";
import type { ClientGrpc } from "@nestjs/microservices";
import { firstValueFrom } from "rxjs";
import type { Response } from "express";
import { Http } from "src/common/http/http.handle.response";
import { PlayerClassMapper } from "../mapper/player-class.mapper";

@Controller('player-class')
export class PlayerClassController implements OnModuleInit {
    private playerClassService: PlayerClassService;
    private readonly logger = new Logger(PlayerClassController.name);

    constructor(@Inject('PLAYER_CLASS_PACKAGE') private readonly client: ClientGrpc) {}

    onModuleInit() {
        this.playerClassService = this.client.getService<PlayerClassService>('PlayerClassService');
    }

    @Get(':id')
    @HttpCode(HttpStatus.OK)
    async getClassById(
        @Param() params: any,
        @Res({ passthrough: true }) res: Response
    ) {
        try {
            const request = {
                classId: params.id,
            };

            const observable = this.playerClassService.GetClassById(request);

            const data = await firstValueFrom(observable);

            this.logger.log(`Devolviendo la clase con ID: ${params.id}`);

            return PlayerClassMapper.toGetClassByIdDTO(data);
        } catch (err) {
            this.logger.error(`Error al obtener la clase con ID: ${params.id}`, err);

            return Http.handleHttpErrorResponse(err, res);
        }
    }

    @Get('scaled-stats/:id')
    @HttpCode(HttpStatus.OK)
    async getScaledClassStats(
        @Param() params: any,
        @Query('hp') hp: number,
        @Query('atk') atk: number,
        @Query('def') def: number,
        @Query('stamina') stamina: number,
        @Query('accuracy') accuracy: number,
        @Query('evasion') evasion: number,
        @Query('level') level: number,
        @Res({ passthrough: true }) res: Response,
    ) {
        try {
            const request = {
                classId: params.id,
                bonus: {
                    hp: hp || 0,
                    atk: atk || 0,
                    def: def || 0,
                    stamina: stamina || 0,
                    accuracy: accuracy || 0,
                    evasion: evasion || 0,
                },
                level: level || 1
            };

            const observable = this.playerClassService.GetScaledClassStats(request);

            const data = await firstValueFrom(observable);

            this.logger.log(`Devolviendo las estadísticas escaladas de la clase con ID: ${params.id}`);

            return data;
        } catch (err) {
            this.logger.error(`Error al obtener las estadísticas escaladas de la clase con ID: ${params.id}`, err);

            return Http.handleHttpErrorResponse(err, res);
        }
    }

    @Get()
    @HttpCode(HttpStatus.OK)
    async listClasses(
        @Res({ passthrough: true }) res: Response,
    ) {
        try {
            const request = {};

            const observable = this.playerClassService.ListClasses(request);

            const data = await firstValueFrom(observable);

            this.logger.log('Devolviendo la lista de clases de jugador');

            return PlayerClassMapper.toListClassesDTO(data);
        } catch (err) {
            this.logger.error('Error al listar las clases de jugador', err);

            return Http.handleHttpErrorResponse(err, res);
        }
    }
}