import { Module } from '@nestjs/common';
import { Transport } from '@nestjs/microservices/enums/transport.enum';
import { ClientsModule } from '@nestjs/microservices/module/clients.module';
import { InventoryController } from '../controller/inventory.controller';

@Module({
  imports: [
    ClientsModule.register([
      {
        name: 'INVENTORY_PACKAGE',
        transport: Transport.GRPC,
        options: {
          url: 'inventory:9093',
          package: 'Inventory',
          protoPath: 'src/inventory/proto/inventory.proto',
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
  controllers: [InventoryController],
})
export class InventoryModule {}
