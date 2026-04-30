import { ClientsModule, Transport } from "@nestjs/microservices";
import { EnemyController } from "../controller/enemy.controller";
import { Module } from "@nestjs/common";

@Module({
    imports: [
        ClientsModule.register([
            {
                name: 'ENEMY_PACKAGE',
                transport: Transport.GRPC,
                options: {
                    url: 'world:9097',
                    package: 'World',
                    protoPath: 'src/world/proto/enemy.proto'
                }
            }
        ])
    ],
    controllers: [EnemyController]
})
export class EnemyModule {}
