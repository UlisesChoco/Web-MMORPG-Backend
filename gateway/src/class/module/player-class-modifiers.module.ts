import { Module } from "@nestjs/common";
import { PlayerClassModifiersController } from "../controller/player-class-modifiers.controller";
import { ClientsModule, Transport } from "@nestjs/microservices";

@Module({
    imports: [
        ClientsModule.register([
            {
                name: 'PLAYER_CLASS_MODIFIER_PACKAGE',
                transport: Transport.GRPC,
                options: {
                    url: 'class:9091',
                    package: 'Class',
                    protoPath: 'src/class/proto/player_class_modifier.proto'
                }
            }
        ])
    ],
    controllers: [PlayerClassModifiersController]
})
export class PlayerClassModifiersModule {

}
