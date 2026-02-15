import { Body, Controller, Get, Inject, Logger, OnModuleInit, Param, Post, Res } from "@nestjs/common";
import type { ClientGrpc } from "@nestjs/microservices";
import { PlayerService } from "../service/player.service";
import type { Response } from "express";
import { Http } from "src/common/http/http.handle.response";
import { firstValueFrom } from "rxjs";
import { PlayerMapper } from "../mapper/player.mapper";
import { UpdatePlayerRequestDTO } from "../dto/update-player-request.dto";
import { DeletePlayerRequestDTO } from "../dto/delete-player-request.dto";

@Controller('player')
export class PlayerController implements OnModuleInit {
    private playerService: PlayerService;
    private readonly logger = new Logger(PlayerController.name);

    constructor(@Inject('PLAYER_PACKAGE') private readonly client: ClientGrpc) {}

    onModuleInit() {
        this.playerService = this.client.getService<PlayerService>('PlayerService');
    }

    @Get(':id')
    async getPlayerById(
        @Param() params: any,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                playerId: params.id
            };

            const observable = this.playerService.GetPlayerById(request);

            const data = await firstValueFrom(observable);

            const finalData = PlayerMapper.toPlayerResponseDTO(data);

            this.logger.log(`Jugador con id ${params.id} obtenido exitosamente`);

            return finalData;
        } catch (err) {
            this.logger.error(`Error al obtener el jugador con id ${params.id}: ${err.message}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }

    @Get('user/:userId')
    async getPlayerByUserId(
        @Param() params: any,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                userId: params.userId
            };

            const observable = this.playerService.GetPlayerByUserId(request);

            const data = await firstValueFrom(observable);

            const finalData = PlayerMapper.toPlayerResponseDTO(data);

            this.logger.log(`Jugador con userId ${params.userId} obtenido exitosamente`);

            return finalData;
        } catch (err) {
            this.logger.error(`Error al obtener el jugador con userId ${params.userId}: ${err.message}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }

    @Get('top/:limit/:alive')
    async getTopPlayersByLevel(
        @Param() params: any,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                limit: params.limit,
                alive: params.alive === 'true'
            };

            const observable = this.playerService.GetTopPlayersByLevel(request);

            const data = await firstValueFrom(observable);

            const finalData = PlayerMapper.toTopPlayersResponseDTO(data);

            this.logger.log(`Top ${request.limit} jugadores obtenidos exitosamente`);

            return finalData;
        } catch (err) {
            this.logger.error(`Error al obtener el top ${params.limit} jugadores: ${err.message}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }

    @Post('update')
    async updatePlayer(
        @Body() dto: UpdatePlayerRequestDTO,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = PlayerMapper.toUpdatePlayerRequest(dto);

            const observable = this.playerService.UpdatePlayer(request);

            const data = await firstValueFrom(observable);

            this.logger.log(`Jugador con id ${request.playerId} actualizado exitosamente`);

            return data;
        } catch (err) {
            this.logger.error(`Error al actualizar el jugador con id ${dto.playerId}: ${err.message}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }

    @Post('delete')
    async deletePlayer(
        @Body() dto: DeletePlayerRequestDTO,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                playerId: dto.playerId
            };

            const observable = this.playerService.DeletePlayer(request);

            const data = await firstValueFrom(observable);

            this.logger.log(`Jugador con id ${request.playerId} eliminado exitosamente`);

            return data;
        } catch (err) {
            this.logger.error(`Error al eliminar el jugador con id ${dto.playerId}: ${err.message}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }
}