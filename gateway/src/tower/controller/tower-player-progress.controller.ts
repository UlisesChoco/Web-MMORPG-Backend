import {
  Body,
  Controller,
  ForbiddenException,
  Get,
  Inject,
  Logger,
  OnModuleInit,
  Param,
  ParseIntPipe,
  Post,
  Req,
  Res,
} from '@nestjs/common';
import { TowerPlayerProgressService } from '../service/tower-player-progress.service';
import { PlayerService } from 'src/player/service/player.service';
import type { ClientGrpc } from '@nestjs/microservices';
import type { Response } from 'express';
import { Http } from 'src/common/http/http.handle.response';
import { OwnershipGuard } from 'src/common/auth/ownership.guard';
import { firstValueFrom } from 'rxjs';
import { TowerPlayerProgressMapper } from '../mapper/tower-player-progress.mapper';
import { RegisterPlayerTowerProgressRequestDTO } from '../dto/tower_player_progress/register-player-tower-progress-request.dto';

@Controller('tower-player-progress')
export class TowerPlayerProgressController implements OnModuleInit {
  private towerPlayerProgressService: TowerPlayerProgressService;
  private playerService: PlayerService;
  private readonly logger = new Logger(TowerPlayerProgressController.name);

  constructor(
    @Inject('TOWER_PLAYER_PROGRESS_PACKAGE')
    private readonly towerClient: ClientGrpc,
    @Inject('PLAYER_PACKAGE') private readonly playerClient: ClientGrpc,
  ) {}

  onModuleInit() {
    this.towerPlayerProgressService =
      this.towerClient.getService<TowerPlayerProgressService>(
        'TowerPlayerProgressService',
      );
    this.playerService =
      this.playerClient.getService<PlayerService>('PlayerService');
  }

  @Get(':playerId')
  async getPlayerTowerProgress(
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
        'getPlayerTowerProgress',
      );

      const observable = this.towerPlayerProgressService.GetPlayerTowerProgress(
        { playerId } as any,
      );
      const data = await firstValueFrom(observable);
      const finalData =
        TowerPlayerProgressMapper.toGetPlayerTowerProgressResponseDTO(data);

      this.logger.log(
        `Progreso de la torre para el jugador ${playerId} obtenido exitosamente.`,
      );

      return finalData;
    } catch (err) {
      if (err instanceof ForbiddenException) throw err;
      this.logger.error(
        `Error al obtener el progreso de la torre para el jugador ${playerId}: ${err.message}`,
      );
      return Http.handleHttpErrorResponse(err, response);
    }
  }

  @Post('register')
  async registerPlayerTowerProgress(
    @Body() dto: RegisterPlayerTowerProgressRequestDTO,
    @Req() req: any,
    @Res({ passthrough: true }) response: Response,
  ) {
    try {
      await OwnershipGuard.verifyPlayerOwnership(
        this.playerService,
        req.user.userId,
        dto.playerId,
        this.logger,
        'registerPlayerTowerProgress',
      );

      const request =
        TowerPlayerProgressMapper.toRegisterPlayerTowerProgressRequest(dto);
      const observable =
        this.towerPlayerProgressService.RegisterPlayerTowerProgress(request);
      const data = await firstValueFrom(observable);

      this.logger.log(
        `Progreso de la torre para el jugador ${dto.playerId} registrado exitosamente.`,
      );

      return data;
    } catch (err) {
      if (err instanceof ForbiddenException) throw err;
      this.logger.error(
        `Error al registrar el progreso de la torre para el jugador ${dto.playerId}: ${err.message}`,
      );
      return Http.handleHttpErrorResponse(err, response);
    }
  }
}
