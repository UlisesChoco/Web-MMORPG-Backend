import { Module } from "@nestjs/common";
import { Transport } from "@nestjs/microservices/enums/transport.enum";
import { ClientsModule } from "@nestjs/microservices/module/clients.module";
import { LootController } from "../controller/loot.controller";

@Module({
    imports: [
        ClientsModule.register([
            {
                name: 'LOOT_PACKAGE',
                transport: Transport.GRPC,
                options: {
                    url: 'loot:9094',
                    package: 'Loot',
                    protoPath: 'src/loot/proto/loot.proto'
                }
            }
        ])
    ],
    controllers: [LootController]
})
export class LootModule {}
