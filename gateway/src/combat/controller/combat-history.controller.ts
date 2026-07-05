import {
  Controller,
  ForbiddenException,
  Get,
  HttpCode,
  HttpStatus,
  Inject,
  Logger,
  OnModuleInit,
  Param,
  ParseIntPipe,
  Req,
  Res,
} from '@nestjs/common';
import { CombatHistoryService } from '../service/combat-history.service';
import { PlayerService } from 'src/player/service/player.service';
import type { ClientGrpc } from '@nestjs/microservices';
import type { Response } from 'express';
import { Http } from 'src/common/http/http.handle.response';
import { OwnershipGuard } from 'src/common/auth/ownership.guard';
import { firstValueFrom } from 'rxjs';
import { CombatHistoryMapper } from '../mapper/combat-history.mapper';

@Controller('combat-history')
export class CombatHistoryController implements OnModuleInit {
  private combatHistoryService: CombatHistoryService;
  private playerService: PlayerService;
  private readonly logger = new Logger(CombatHistoryController.name);

  constructor(
    @Inject('COMBAT_HISTORY_PACKAGE')
    private readonly combatHistoryClient: ClientGrpc,
    @Inject('PLAYER_PACKAGE') private readonly playerClient: ClientGrpc,
  ) {}

  onModuleInit() {
    this.combatHistoryService =
      this.combatHistoryClient.getService<CombatHistoryService>(
        'CombatHistoryService',
      );
    this.playerService =
      this.playerClient.getService<PlayerService>('PlayerService');
  }

  @Get('by-player/:id')
  @HttpCode(HttpStatus.OK)
  async getCombatHistoryByPlayer(
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
        'getCombatHistoryByPlayer',
      );

      const request = { playerId: id };
      const observable =
        this.combatHistoryService.GetCombatHistoryByPlayer(request);
      const data = await firstValueFrom(observable);

      this.logger.log(
        `Historial de combate obtenido para el jugador de ID ${id}`,
      );

      return CombatHistoryMapper.toGetCombatHistoryByPlayerDTO(data);
    } catch (err) {
      if (err instanceof ForbiddenException) throw err;
      this.logger.error(
        `Error al obtener el historial de combate del jugador ${id}: ${err}`,
      );
      return Http.handleHttpErrorResponse(err, response);
    }
  }

  @Get('recent/:limit')
  @HttpCode(HttpStatus.OK)
  async getRecentCombats(
    @Param('limit', ParseIntPipe) limit: number,
    @Res({ passthrough: true }) response: Response,
  ) {
    try {
      const request = { limit };
      const observable = this.combatHistoryService.GetRecentCombats(request);
      const data = await firstValueFrom(observable);

      this.logger.log(`Obtenidos los ${limit} combates recientes`);

      return CombatHistoryMapper.toGetRecentCombatsDTO(data);
    } catch (err) {
      this.logger.error(`Error al obtener los combates recientes: ${err}`);
      return Http.handleHttpErrorResponse(err, response);
    }
  }

  @Get('win-count/:playerId')
  @HttpCode(HttpStatus.OK)
  async getPlayerWinCount(
    @Param('playerId', ParseIntPipe) playerId: number,
    @Req() req: any,
    @Res({ passthrough: true }) response: Response,
  ) {
    try {
      await OwnershipGuard.verifyPlayerOwnership(
        this.playerService,
        req.user.userId,
        playerId,
        this.logger,
        'getPlayerWinCount',
      );

      const request = { playerId };
      const observable = this.combatHistoryService.GetPlayerWinCount(request);
      const data = await firstValueFrom(observable);

      this.logger.log(
        `Obtenido el conteo de victorias para el jugador de ID ${playerId}`,
      );

      return CombatHistoryMapper.toGetPlayerWinCountDTO(data);
    } catch (err) {
      if (err instanceof ForbiddenException) throw err;
      this.logger.error(
        `Error al obtener el conteo de victorias del jugador ${playerId}: ${err}`,
      );
      return Http.handleHttpErrorResponse(err, response);
    }
  }
}
