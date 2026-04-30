import { Module } from "@nestjs/common";
import { CombatController } from "../controller/combat.controller";
import { ClientsModule, Transport } from "@nestjs/microservices";

@Module({
    imports: [
        ClientsModule.register([
            {
                name: 'COMBAT_PACKAGE',
                transport: Transport.GRPC,
                options: {
                    url: 'combat:9092',
                    package: 'Combat',
                    protoPath: 'src/combat/proto/combat.proto'
                }
            }
        ])
    ],
    controllers: [CombatController]
})
export class CombatModule {}
