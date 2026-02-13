import { ClientsModule, Transport } from "@nestjs/microservices";
import { Module } from "@nestjs/common";
import { NpcController } from "../controller/npc.controller";

@Module({
    imports: [
        ClientsModule.register([
            {
                name: 'NPC_PACKAGE',
                transport: Transport.GRPC,
                options: {
                    url: 'localhost:9097',
                    package: 'World',
                    protoPath: 'src/world/proto/npc.proto'
                }
            }
        ])
    ],
    controllers: [NpcController]
})
export class NpcModule {}