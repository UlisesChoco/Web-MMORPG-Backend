import { Module } from "@nestjs/common";
import { ClientsModule, Transport } from "@nestjs/microservices";
import { FatalCombatReplayController } from "../controller/fatal-combat-replay.controller";

@Module({
    imports: [
        ClientsModule.register([
            {
                name: 'FATAL_COMBAT_REPLAY_PACKAGE',
                transport: Transport.GRPC,
                options: {
                    url: 'combat:9092',
                    package: 'Combat',
                    protoPath: 'src/combat/proto/fatal_combat_replay.proto'
                }
            }
        ])
    ],
    controllers: [FatalCombatReplayController]
})
export class FatalCombatReplayModule {}
