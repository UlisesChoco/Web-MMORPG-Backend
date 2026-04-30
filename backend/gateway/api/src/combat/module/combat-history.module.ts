import { Module } from "@nestjs/common";
import { CombatHistoryController } from "../controller/combat-history.controller";
import { ClientsModule, Transport } from "@nestjs/microservices";

@Module({
    imports: [
        ClientsModule.register([
            {
                name: 'COMBAT_HISTORY_PACKAGE',
                transport: Transport.GRPC,
                options: {
                    url: 'combat:9092',
                    package: 'Combat',
                    protoPath: 'src/combat/proto/combat_history.proto'
                }
            }
        ])
    ],
    controllers: [CombatHistoryController]
})
export class CombatHistoryModule {}
