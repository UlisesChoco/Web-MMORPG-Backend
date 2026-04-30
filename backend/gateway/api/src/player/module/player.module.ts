import { Module } from "@nestjs/common";
import { Transport } from "@nestjs/microservices/enums/transport.enum";
import { ClientsModule } from "@nestjs/microservices/module/clients.module";
import { PlayerController } from "../controller/player.controller";

@Module({
    imports: [
        ClientsModule.register([
            {
                name: 'PLAYER_PACKAGE',
                transport: Transport.GRPC,
                options: {
                    url: 'player:9095',
                    package: 'Player',
                    protoPath: 'src/player/proto/player.proto'
                }
            }
        ])
    ],
    controllers: [PlayerController]
})
export class PlayerModule {}
