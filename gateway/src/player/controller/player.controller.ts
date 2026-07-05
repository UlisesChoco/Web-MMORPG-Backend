import {
  Body,
  Controller,
  ForbiddenException,
  Get,
  Inject,
  Logger,
  OnModuleInit,
  Param,
  Post,
  Req,
  Res,
  ParseIntPipe,
} from '@nestjs/common';
import type { ClientGrpc } from '@nestjs/microservices';
import { PlayerService } from '../service/player.service';
import type { Request, Response } from 'express';
import { Http } from 'src/common/http/http.handle.response';
import { OwnershipGuard } from 'src/common/auth/ownership.guard';
import { firstValueFrom } from 'rxjs';
import { PlayerMapper } from '../mapper/player.mapper';
import { UpdatePlayerRequestDTO } from '../dto/update-player-request.dto';
import { DeletePlayerRequestDTO } from '../dto/delete-player-request.dto';

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
    @Param('id', ParseIntPipe) id: number,
    @Req() req: any,
    @Res({ passthrough: true }) response: Response,
  ) {
    try {
      await OwnershipGuard.verifyPlayerOwnership(
        this.playerService,
        req.user.userId,
        id,
        this.logger,
        'getPlayerById',
      );

      const observable = this.playerService.GetPlayerById({
        playerId: id,
      } as any);
      const data = await firstValueFrom(observable);
      const finalData = PlayerMapper.toPlayerResponseDTO(data);

      this.logger.log(`Jugador con id ${id} obtenido exitosamente`);

      return finalData;
    } catch (err) {
      if (err instanceof ForbiddenException) throw err;
      this.logger.error(
        `Error al obtener el jugador con id ${id}: ${err.message}`,
      );
      return Http.handleHttpErrorResponse(err, response);
    }
  }

  @Get('user')
  async getPlayerByUserId(
    @Req() req: any,
    @Res({ passthrough: true }) response: Response,
  ) {
    try {
      const request = { userId: req.user.userId };
      const observable = this.playerService.GetPlayerByUserId(request);
      const data = await firstValueFrom(observable);
      const finalData = PlayerMapper.toPlayerResponseDTO(data);

      this.logger.log(
        `Jugador con userId ${req.user.userId} obtenido exitosamente`,
      );

      return finalData;
    } catch (err) {
      this.logger.error(
        `Error al obtener el jugador con userId ${req.user.userId}: ${err.message}`,
      );
      return Http.handleHttpErrorResponse(err, response);
    }
  }

  @Get('top/:limit/:alive')
  async getTopPlayersByLevel(
    @Param('limit', ParseIntPipe) limit: number,
    @Param('alive') alive: string,
    @Res({ passthrough: true }) response: Response,
  ) {
    try {
      const request = {
        limit,
        alive: alive === 'true',
      };

      const observable = this.playerService.GetTopPlayersByLevel(request);
      const data = await firstValueFrom(observable);
      const finalData = PlayerMapper.toTopPlayersResponseDTO(data);

      this.logger.log(`Top ${limit} jugadores obtenidos exitosamente`);

      return finalData;
    } catch (err) {
      this.logger.error(
        `Error al obtener el top ${limit} jugadores: ${err.message}`,
      );
      return Http.handleHttpErrorResponse(err, response);
    }
  }

  @Post('update')
  async updatePlayer(
    @Body() dto: UpdatePlayerRequestDTO,
    @Req() req: any,
    @Res({ passthrough: true }) response: Response,
  ) {
    try {
      await OwnershipGuard.verifyPlayerOwnership(
        this.playerService,
        req.user.userId,
        dto.playerId,
        this.logger,
        'updatePlayer',
      );

      const request = PlayerMapper.toUpdatePlayerRequest(dto);
      const observable = this.playerService.UpdatePlayer(request);
      const data = await firstValueFrom(observable);

      this.logger.log(
        `Jugador con id ${dto.playerId} actualizado exitosamente`,
      );

      return data;
    } catch (err) {
      if (err instanceof ForbiddenException) throw err;
      this.logger.error(
        `Error al actualizar el jugador con id ${dto.playerId}: ${err.message}`,
      );
      return Http.handleHttpErrorResponse(err, response);
    }
  }

  @Post('delete')
  async deletePlayer(
    @Body() dto: DeletePlayerRequestDTO,
    @Req() req: any,
    @Res({ passthrough: true }) response: Response,
  ) {
    try {
      await OwnershipGuard.verifyPlayerOwnership(
        this.playerService,
        req.user.userId,
        dto.playerId,
        this.logger,
        'deletePlayer',
      );

      const observable = this.playerService.DeletePlayer({
        playerId: dto.playerId,
      } as any);
      const data = await firstValueFrom(observable);

      this.logger.log(`Jugador con id ${dto.playerId} eliminado exitosamente`);

      return data;
    } catch (err) {
      if (err instanceof ForbiddenException) throw err;
      this.logger.error(
        `Error al eliminar el jugador con id ${dto.playerId}: ${err.message}`,
      );
      return Http.handleHttpErrorResponse(err, response);
    }
  }
}
