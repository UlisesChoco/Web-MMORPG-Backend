import { Module } from '@nestjs/common';
import { CombatHistoryController } from '../controller/combat-history.controller';
import { ClientsModule, Transport } from '@nestjs/microservices';

@Module({
  imports: [
    ClientsModule.register([
      {
        name: 'COMBAT_HISTORY_PACKAGE',
        transport: Transport.GRPC,
        options: {
          url: 'combat:9092',
          package: 'Combat',
          protoPath: 'src/combat/proto/combat_history.proto',
        },
      },
      {
        name: 'PLAYER_PACKAGE',
        transport: Transport.GRPC,
        options: {
          url: 'player:9095',
          package: 'Player',
          protoPath: 'src/player/proto/player.proto',
        },
      },
    ]),
  ],
  controllers: [CombatHistoryController],
})
export class CombatHistoryModule {}
