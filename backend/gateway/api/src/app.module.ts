import { MiddlewareConsumer, Module, NestModule, RequestMethod } from '@nestjs/common';
import { AuthModule } from './auth/auth.module';
import { AuthMiddleware } from './auth/auth.middleware';
import { PlayerClassModule } from './class/module/player-class.module';
import { PlayerClassModifiersModule } from './class/module/player-class-modifiers.module';
import { CombatModule } from './combat/module/combat.module';
import { CombatHistoryModule } from './combat/module/combat-history.module';
import { FatalCombatReplayModule } from './combat/module/fatal-combat-replay.module';
import { InventoryModule } from './inventory/module/inventory.module';
import { ItemModule } from './inventory/module/item.module';
import { LootModule } from './loot/module/loot.module';
import { PlayerModule } from './player/module/player.module';

@Module({
  imports: [
    AuthModule,
    PlayerClassModule, PlayerClassModifiersModule,
    CombatModule, CombatHistoryModule, FatalCombatReplayModule,
    InventoryModule, ItemModule,
    LootModule,
    PlayerModule
  ],
})
export class AppModule implements NestModule {
  configure(consumer: MiddlewareConsumer) {
    consumer
      .apply(AuthMiddleware)
      .exclude({ path: '/auth/*path', method: RequestMethod.ALL })
      .forRoutes('*');
  }
}
