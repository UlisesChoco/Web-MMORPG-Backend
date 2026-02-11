import { Module } from "@nestjs/common";
import { TowerController } from "../controller/tower.controller";
import { ClientsModule, Transport } from "@nestjs/microservices";

@Module({
    imports: [
        ClientsModule.register([
            {
                name: 'TOWER_PACKAGE',
                transport: Transport.GRPC,
                options: {
                    url: 'localhost:9096',
                    package: 'Tower',
                    protoPath: 'src/tower/proto/tower.proto'
                }
            }
        ])
    ],
    controllers: [TowerController]
})
export class TowerModule {}