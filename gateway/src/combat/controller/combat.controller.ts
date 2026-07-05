import {
  Body,
  Controller,
  ForbiddenException,
  HttpCode,
  HttpStatus,
  Inject,
  Logger,
  OnModuleInit,
  Post,
  Req,
  Res,
} from '@nestjs/common';
import { CombatService } from '../service/combat.service';
import { PlayerService } from 'src/player/service/player.service';
import type { ClientGrpc } from '@nestjs/microservices';
import { ProcessCombatDTO } from '../dto/combat/process-combat.dto';
import { ProcessCombatRequest } from '../interfaces/combat/request/process-combat-request.interface';
import { firstValueFrom } from 'rxjs';
import { Http } from 'src/common/http/http.handle.response';
import { OwnershipGuard } from 'src/common/auth/ownership.guard';
import type { Response } from 'express';
import { CombatMapper } from '../mapper/combat.mapper';

@Controller('combat')
export class CombatController implements OnModuleInit {
  private combatService: CombatService;
  private playerService: PlayerService;
  private readonly logger = new Logger(CombatController.name);

  constructor(
    @Inject('COMBAT_PACKAGE') private readonly combatClient: ClientGrpc,
    @Inject('PLAYER_PACKAGE') private readonly playerClient: ClientGrpc,
  ) {}

  onModuleInit() {
    this.combatService =
      this.combatClient.getService<CombatService>('CombatService');
    this.playerService =
      this.playerClient.getService<PlayerService>('PlayerService');
  }

  @Post('process')
  @HttpCode(HttpStatus.OK)
  async processCombat(
    @Body() dto: ProcessCombatDTO,
    @Req() req: any,
    @Res({ passthrough: true }) response: Response,
  ) {
    try {
      await OwnershipGuard.verifyPlayerOwnership(
        this.playerService,
        req.user.userId,
        dto.playerId,
        this.logger,
        'processCombat',
      );

      const request: ProcessCombatRequest = {
        playerId: dto.playerId,
        enemyId: dto.enemyId,
      };

      const observable = this.combatService.ProcessCombat(request);
      const data = await firstValueFrom(observable);
      const finalData = CombatMapper.toProcessCombatResponseDTO(data);

      this.logger.log(
        `Se procesó un combate entre el jugador ${dto.playerId} y el enemigo ${dto.enemyId}`,
      );

      return finalData;
    } catch (err) {
      if (err instanceof ForbiddenException) throw err;
      this.logger.error(
        `Error al procesar combate entre el jugador ${dto.playerId} y el enemigo ${dto.enemyId}: ${err}`,
      );
      return Http.handleHttpErrorResponse(err, response);
    }
  }
}
