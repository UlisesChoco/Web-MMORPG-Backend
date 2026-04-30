import { ClientsModule, Transport } from "@nestjs/microservices";
import { Module } from "@nestjs/common";
import { NpcItemController } from "../controller/npc-item.controller";

@Module({
    imports: [
        ClientsModule.register([
            {
                name: 'NPC_ITEM_PACKAGE',
                transport: Transport.GRPC,
                options: {
                    url: 'world:9097',
                    package: 'World',
                    protoPath: 'src/world/proto/npc_item.proto'
                }
            }
        ])
    ],
    controllers: [NpcItemController]
})
export class NpcItemModule {}
