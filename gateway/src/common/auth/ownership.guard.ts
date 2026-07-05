import { ForbiddenException, Logger } from '@nestjs/common';
import { firstValueFrom } from 'rxjs';
import { Util } from 'src/common/util/number.functions';

export class OwnershipGuard {
  static async verifyPlayerOwnership(
    playerService: any,
    userId: number,
    requestedPlayerId: number,
    logger: Logger,
    context: string,
  ): Promise<void> {
    const observable = playerService.GetPlayerByUserId({ userId });
    const data: any = await firstValueFrom(observable);

    const playerId = data.player?.id;
    if (
      playerId == null ||
      Number(Util.longToNumber(playerId)) !== Number(requestedPlayerId)
    ) {
      logger.warn(
        `[${context}] Ownership violation: user ${userId} tried to access player ${requestedPlayerId}`,
      );
      throw new ForbiddenException(
        'No tienes permiso para acceder a este recurso',
      );
    }
  }
}
