import { Module } from "@nestjs/common";
import { ClientsModule, Transport } from "@nestjs/microservices";
import { TowerPlayerProgressController } from "../controller/tower-player-progress.controller";

@Module({
    imports: [
        ClientsModule.register([
            {
                name: 'TOWER_PLAYER_PROGRESS_PACKAGE',
                transport: Transport.GRPC,
                options: {
                    url: 'localhost:9096',
                    package: 'Tower',
                    protoPath: 'src/tower/proto/tower_player_progress.proto'
                }
            }
        ])
    ],
    controllers: [TowerPlayerProgressController]
})
export class TowerPlayerProgressModule {}