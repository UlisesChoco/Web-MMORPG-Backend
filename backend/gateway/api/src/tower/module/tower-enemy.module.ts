import { Module } from "@nestjs/common";
import { ClientsModule, Transport } from "@nestjs/microservices";
import { TowerEnemyController } from "../controller/tower-enemy.controller";

@Module({
    imports: [
        ClientsModule.register([
            {
                name: 'TOWER_ENEMY_PACKAGE',
                transport: Transport.GRPC,
                options: {
                    url: 'tower:9096',
                    package: 'Tower',
                    protoPath: 'src/tower/proto/tower_enemy.proto'
                }
            }
        ])
    ],
    controllers: [TowerEnemyController]
})
export class TowerEnemyModule {}
